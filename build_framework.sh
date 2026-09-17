#!/bin/sh
# This script is called by Xcode build phase to bypass permission issues with gradlew
cd "$(dirname "$0")"
chmod +x gradlew
sh ./gradlew :composeApp:embedAndSignAppleFrameworkForXcode
