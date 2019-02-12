package net.ukr.vlsv.ippon_library.info

public class ippon_Info() {
    public fun getActivityTitle(appName: String, versionName: String): String {
        return appName + " v " + versionName
    }

}