package com.jihan.monitor.sdk.utils;


import android.os.RemoteException;

/**
 * Helper functions to assist remote calls.
 *
 */
public abstract class Remote {

    private static final String TAG = SdkLogUtils.TAG_SDK + Remote.class.getSimpleName();

    /**
     * Throwing void function.
     */
    public interface RemoteVoidFunction {
        /**
         * The actual throwing function.
         */
        void call() throws RemoteException;
    }

    /**
     * Throwing function that returns some value.
     *
     * @param <V> Return type for the function.
     */
    public interface RemoteFunction<V> {
        /**
         * The actual throwing function.
         */
        V call() throws RemoteException;
    }

    /**
     * Wraps remote function and rethrows {@link RemoteException}.
     */
    public static <V> V exec(RemoteFunction<V> func) {
        try {
            return func.call();
        } catch (RemoteException exception) {
            throw new IllegalArgumentException("Failed to execute remote call" + exception);
        }
    }

    /**
     * Wraps remote void function and logs in case of {@link RemoteException}.
     */
    public static void tryExec(RemoteVoidFunction func) {
        try {
            func.call();
        } catch (RemoteException exception) {
            SdkLogUtils.logV(TAG, exception.toString());
        }
    }
}
