package com.lmuro.boqez

import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion()
    override val deviceName: String = UIDevice.currentDevice.name
    override val deviceId: String = UIDevice.currentDevice.identifierForVendor?.UUIDString ?: ""
}
