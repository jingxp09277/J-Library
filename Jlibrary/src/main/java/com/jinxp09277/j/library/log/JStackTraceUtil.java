package com.jinxp09277.j.library.log;

/**
 * Created by LWB on 2020/10/28
 */
public class JStackTraceUtil {
    public static StackTraceElement[] getCroppedRealStackTrack(StackTraceElement[] stackTrace, String ignroePackage, int maxDepth) {
        return cropStackTrace(getRealStackTrack(stackTrace, ignroePackage), maxDepth);
    }

    /**
     * 获取除忽略包名之外的信息
     *
     * @param stackTrace
     * @param ignorePackage
     * @return
     */
    private static StackTraceElement[] getRealStackTrack(StackTraceElement[] stackTrace, String ignorePackage) {
        int ignotreDepth = 0;
        int allDepth = stackTrace.length;
        String className;
        for (int i = allDepth - 1; i >= 0; i--) {
            className = stackTrace[i].getClassName();
            if (ignorePackage != null && className.startsWith(ignorePackage)) {
                ignotreDepth = i + 1;
                break;
            }
        }
        int realDepth = allDepth - ignotreDepth;
        StackTraceElement[] realStack = new StackTraceElement[realDepth];
        System.arraycopy(stackTrace, ignotreDepth, realStack, 0, realDepth);
        return realStack;
    }

    /**
     * 裁剪堆栈信息
     *
     * @param callStack
     * @param maxDepth
     * @return
     */
    private static StackTraceElement[] cropStackTrace(StackTraceElement[] callStack, int maxDepth) {
        int realDepth = callStack.length;
        if (maxDepth > 0) {
            realDepth = Math.min(maxDepth, realDepth);
        }
        StackTraceElement[] realStack = new StackTraceElement[realDepth];
        System.arraycopy(callStack, 0, realStack, 0, realDepth);
        return realStack;
    }
}
