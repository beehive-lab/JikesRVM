/*
 *  This file is part of the Jikes RVM project (http://jikesrvm.org).
 *
 *  This file is licensed to You under the Eclipse Public License (EPL);
 *  You may not use this file except in compliance with the License. You
 *  may obtain a copy of the License at
 *
 *      http://www.opensource.org/licenses/eclipse-1.0.php
 *
 *  See the COPYRIGHT.txt file distributed with this work for information
 *  regarding copyright ownership.
 */
package java.lang;

import java.security.ProtectionDomain;
import java.lang.instrument.Instrumentation;

import org.jikesrvm.classloader.Atom;
import org.jikesrvm.classloader.RVMType;
import org.jikesrvm.classloader.RVMField;

import org.vmmagic.pragma.*;
import org.vmmagic.unboxed.Offset;

import org.jikesrvm.scheduler.RVMThread;

/**
 * Library support interface of Jikes RVM
 */
public class JikesRVMSupport {

  private static final RVMField JavaLangStringCharsField = RVMType.JavaLangStringType.findDeclaredField(Atom.findAsciiAtom("value"));
  private static final RVMField JavaLangStringOffsetField = RVMType.JavaLangStringType.findDeclaredField(Atom.findAsciiAtom("offset"));
  private static final Offset STRING_CHARS_OFFSET = JavaLangStringCharsField.getOffset();
  private static final Offset STRING_OFFSET_OFFSET = JavaLangStringOffsetField.getOffset();

  /**
   * Call the Object finalize method on the given object
   */
  public static void invokeFinalize(Object o)  throws Throwable {
    o.finalize();
  }

  public static void initializeInstrumentation(Instrumentation instrumenter) {
    throw new Error("InitializeInstrumentation is not implemented");
    //    VMClassLoader.setInstrumenter(instrumenter);
  }

  public static Class<?>[] getAllLoadedClasses() {
    //    VM.sysWriteln("get allloadedclasses is not implemented");
    throw new Error("GetAllLoadedClasses is not implemented");
  }

  public static Class<?>[] getInitiatedClasses(ClassLoader classLoader) {
    //    return VMClassLoader.getInitiatedClasses(classLoader);
    throw new Error("GetInitiatedClasses is not implemented");
  }

  public static Class<?> createClass(RVMType type) {
    return Class.create(type);
  }

  public static Class<?> createClass(RVMType type, ProtectionDomain pd) {
    Class<?> c = Class.create(type);
    setClassProtectionDomain(c, pd);
    return c;
  }

  public static RVMType getTypeForClass(Class<?> c) {
    return c.type;
  }

  public static void setClassProtectionDomain(Class<?> c, ProtectionDomain pd) {
    c.pd = pd;
  }

  /***
   * String stuff
   * */

  @Uninterruptible
  public static char[] getBackingCharArray(String str) {
    return str.value;
  }

  @Uninterruptible
  public static int getStringLength(String str) {
    return str.count;
  }

  @Uninterruptible
  public static int getStringOffset(String str) {
    return str.offset;
  }

  public static String newStringWithoutCopy(char[] data, int offset, int count) {
    //No back door
    return new String(data, offset, count);
  }

  /***
   * Thread stuff
   * */
  public static Thread createThread(RVMThread vmdata, String myName) {
      return new Thread(vmdata, myName);
  }

  public static RVMThread getThread(Thread thread) {
    if (thread == null)
      return null;
    else
      return thread.vmThread;
  }

  public static void threadDied(Thread thread) {

  }
  public static Throwable getStillBorn(Thread thread) {
    return null;
  }
  public static void setStillBorn(Thread thread, Throwable stillborn) {
  }
  /***
   * Enum stuff
   */
   @Uninterruptible
  public static int getEnumOrdinal(Enum<?> e) {
     return e.ordinal();
   }
  @Uninterruptible
  public static String getEnumName(Enum<?> e) {
    return e.name();
  }
}