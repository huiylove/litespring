package org.litespring.util;

/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import java.beans.Introspector;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Miscellaneous class utility methods.
 * Mainly for internal use within the framework.
 *
 * @author Juergen Hoeller
 * @author Keith Donald
 * @author Rob Harrop
 * @author Sam Brannen
 * @since 1.1
 * @see TypeUtils
 * @see ReflectionUtils
 */
public abstract class ClassUtils {

	/** Suffix for array class names: "[]" */
	public static final String ARRAY_SUFFIX = "[]";

	/** Prefix for internal array class names: "[" */
	private static final String INTERNAL_ARRAY_PREFIX = "[";

	/** Prefix for internal non-primitive array class names: "[L" */
	private static final String NON_PRIMITIVE_ARRAY_PREFIX = "[L";

	/** The package separator character: '.' */
	private static final char PACKAGE_SEPARATOR = '.';

	/** The path separator character: '/' */
	private static final char PATH_SEPARATOR = '/';

	/** The inner class separator character: '$' */
	private static final char INNER_CLASS_SEPARATOR = '$';

	/** The CGLIB class separator: "$$" */
	public static final String CGLIB_CLASS_SEPARATOR = "$$";

	/** The ".class" file suffix */
	public static final String CLASS_FILE_SUFFIX = ".class";


	/**
	 * Map with primitive wrapper type as key and corresponding primitive
	 * type as value, for example: Integer.class -> int.class.
	 */
	private static final Map<Class<?>, Class<?>> primitiveWrapperTypeMap = new HashMap<Class<?>, Class<?>>(8);

	/**
	 * Map with primitive type as key and corresponding wrapper
	 * type as value, for example: int.class -> Integer.class.
	 */
	private static final Map<Class<?>, Class<?>> primitiveTypeToWrapperMap = new HashMap<Class<?>, Class<?>>(8);

	/**
	 * Map with primitive type name as key and corresponding primitive
	 * type as value, for example: "int" -> "int.class".
	 */
	private static final Map<String, Class<?>> primitiveTypeNameMap = new HashMap<String, Class<?>>(32);

	/**
	 * Map with common "java.lang" class name as key and corresponding Class as value.
	 * Primarily for efficient deserialization of remote invocations.
	 */
	private static final Map<String, Class<?>> commonClassCache = new HashMap<String, Class<?>>(32);


	static {
		primitiveWrapperTypeMap.put(Boolean.class, boolean.class);
		primitiveWrapperTypeMap.put(Byte.class, byte.class);
		primitiveWrapperTypeMap.put(Character.class, char.class);
		primitiveWrapperTypeMap.put(Double.class, double.class);
		primitiveWrapperTypeMap.put(Float.class, float.class);
		primitiveWrapperTypeMap.put(Integer.class, int.class);
		primitiveWrapperTypeMap.put(Long.class, long.class);
		primitiveWrapperTypeMap.put(Short.class, short.class);

		for (Map.Entry<Class<?>, Class<?>> entry : primitiveWrapperTypeMap.entrySet()) {
			primitiveTypeToWrapperMap.put(entry.getValue(), entry.getKey());
			registerCommonClasses(entry.getKey());
		}

		Set<Class<?>> primitiveTypes = new HashSet<Class<?>>(32);
		primitiveTypes.addAll(primitiveWrapperTypeMap.values());
		primitiveTypes.addAll(Arrays.asList(new Class<?>[] {
				boolean[].class, byte[].class, char[].class, double[].class,
				float[].class, int[].class, long[].class, short[].class}));
		primitiveTypes.add(void.class);
		for (Class<?> primitiveType : primitiveTypes) {
			primitiveTypeNameMap.put(primitiveType.getName(), primitiveType);
		}

		registerCommonClasses(Boolean[].class, Byte[].class, Character[].class, Double[].class,
				Float[].class, Integer[].class, Long[].class, Short[].class);
		registerCommonClasses(Number.class, Number[].class, String.class, String[].class,
				Object.class, Object[].class, Class.class, Class[].class);
		registerCommonClasses(Throwable.class, Exception.class, RuntimeException.class,
				Error.class, StackTraceElement.class, StackTraceElement[].class);
	}


	/**
	 * Register the given common classes with the ClassUtils cache.
	 */
	private static void registerCommonClasses(Class<?>... commonClasses) {
		for (Class<?> clazz : commonClasses) {
			commonClassCache.put(clazz.getName(), clazz);
		}
	}

	/**
	 * Return the default ClassLoader to use: typically the thread context
	 * ClassLoader, if available; the ClassLoader that loaded the ClassUtils
	 * class will be used as fallback.
	 * <p>Call this method if you intend to use the thread context ClassLoader
	 * in a scenario where you clearly prefer a non-null ClassLoader reference:
	 * for example, for class path resource loading (but not necessarily for
	 * {@code Class.forName}, which accepts a {@code null} ClassLoader
	 * reference as well).
	 * @return the default ClassLoader (only {@code null} if even the system
	 * ClassLoader isn't accessible)
	 * @see Thread#getContextClassLoader()
	 * @see ClassLoader#getSystemClassLoader()
	 */
	public static ClassLoader getDefaultClassLoader() {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		}
		catch (Throwable ex) {
			// Cannot access thread context ClassLoader - falling back...
		}
		if (cl == null) {
			// No thread context class loader -> use class loader of this class.
			cl = ClassUtils.class.getClassLoader();
			if (cl == null) {
				// getClassLoader() returning null indicates the bootstrap ClassLoader
				try {
					cl = ClassLoader.getSystemClassLoader();
				}
				catch (Throwable ex) {
					// Cannot access system ClassLoader - oh well, maybe the caller can live with null...
				}
			}
		}
		return cl;
	}

	/**
	 * Override the thread context ClassLoader with the environment's bean ClassLoader
	 * if necessary, i.e. if the bean ClassLoader is not equivalent to the thread
	 * context ClassLoader already.
	 * @param classLoaderToUse the actual ClassLoader to use for the thread context
	 * @return the original thread context ClassLoader, or {@code null} if not overridden
	 */
	public static ClassLoader overrideThreadContextClassLoader(ClassLoader classLoaderToUse) {
		Thread currentThread = Thread.currentThread();
		ClassLoader threadContextClassLoader = currentThread.getContextClassLoader();
		if (classLoaderToUse != null && !classLoaderToUse.equals(threadContextClassLoader)) {
			currentThread.setContextClassLoader(classLoaderToUse);
			return threadContextClassLoader;
		}
		else {
			return null;
		}
	}




}
