QT += quick

# You can make your code fail to compile if it uses deprecated APIs.
# In order to do so, uncomment the following line.
#DEFINES += QT_DISABLE_DEPRECATED_BEFORE=0x060000    # disables all the APIs deprecated before Qt 6.0.0

# need to reinterpret cast this
# how to call C++ member function from java , reinterpret cast
# need to pass pointer to native and get back a class pointer
# search reinterpret_Cast in github to get exammples


SOURCES += \
        filepicker.cpp \
        main.cpp

RESOURCES += qml.qrc

# Additional import path used to resolve QML modules in Qt Creator's code model
QML_IMPORT_PATH =

# Additional import path used to resolve QML modules just for Qt Quick Designer
QML_DESIGNER_IMPORT_PATH =

# Default rules for deployment.
qnx: target.path = /tmp/$${TARGET}/bin
else: unix:!android: target.path = /opt/$${TARGET}/bin
!isEmpty(target.path): INSTALLS += target

android {
    ANDROID_PACKAGE_SOURCE_DIR = $$PWD/android

    DISTFILES += \
        android/src/com/example/march31/MainActivity.java \
        android/AndroidManifest.xml \
        android/build.gradle \
        android/res/values/libs.xml
}

HEADERS += \
    filepicker.h