package org.adaway.feature.debloat;

import android.content.Context;

import com.topjohnwu.superuser.Shell;

import java.util.List;

import timber.log.Timber;

/**
 * Manager for debloating features.
 */
public class DebloatManager {

    private final Context context;

    public DebloatManager(Context context) {
        this.context = context;
    }

    /**
     * Disables the specified package using root.
     *
     * @param packageName The name of the package to disable.
     * @return true if the command executed successfully (exit code 0), false otherwise.
     */
    public boolean disablePackage(String packageName) {
        Timber.d("Attempting to disable package: %s", packageName);
        String command = "pm disable-user --user 0 " + packageName;
        Shell.Result result = Shell.cmd(command).exec();

        boolean success = result.isSuccess();
        if (success) {
            Timber.i("Successfully disabled package: %s", packageName);
        } else {
            Timber.e("Failed to disable package: %s. Output: %s", packageName, result.getOut());
            Timber.e("Error: %s", result.getErr());
        }
        return success;
    }

    /**
     * Disables a list of packages.
     *
     * @param packageNames List of package names to disable.
     */
    public void disablePackages(List<String> packageNames) {
        for (String packageName : packageNames) {
            disablePackage(packageName);
        }
    }
}
