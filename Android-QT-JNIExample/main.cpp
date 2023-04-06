#include <QGuiApplication>
#include <QQmlApplicationEngine>
#include <filepicker.h>
#include <QJniObject>
#include <QJniEnvironment>
#include <QDebug>
#include <QQmlContext>


//void toggleAutoLock(bool value){
//#if defined(Q_OS_ANDROID)
//    auto activity = QJniObject(QNativeInterface::QAndroidApplication::context());
//    if (activity.isValid()) {
//        QJniObject window = activity.callObjectMethod("getWindow", "()Landroid/view/Window;");

//        if (window.isValid()) {
//            const int FLAG_KEEP_SCREEN_ON = 128;
//            if (value == false){
//                qDebug() << "Auto lock is OFF";
//                window.callMethod<void>("addFlags", "(I)V", FLAG_KEEP_SCREEN_ON);
//            } else{
//                qDebug() << "Auto lock is ON";
//                window.callMethod<void>("clearFlags", "(I)V", FLAG_KEEP_SCREEN_ON);
//            }

//        }
//        QJniEnvironment env; if (env->ExceptionCheck()) { env->ExceptionClear(); } //Clear any possible pending exceptions.
//    }
//#endif
//}

int main(int argc, char *argv[])
{
#if QT_VERSION < QT_VERSION_CHECK(6, 0, 0)
    QCoreApplication::setAttribute(Qt::AA_EnableHighDpiScaling);
#endif
    QGuiApplication app(argc, argv);

    qmlRegisterType<FilePicker>("FilePicker",1,0,"FilePicker");

    QQmlApplicationEngine engine;

    //engine.rootContext()->setContextProperty("keepScreenOn", &FilePicker::toggleAutoLock);
    //engine.load(QUrl(QStringLiteral("qrc:/main.qml")));

    const QUrl url(QStringLiteral("qrc:/main.qml"));
    QObject::connect(&engine, &QQmlApplicationEngine::objectCreated,
                     &app, [url](QObject *obj, const QUrl &objUrl) {
        if (!obj && url == objUrl)
            QCoreApplication::exit(-1);
    }, Qt::QueuedConnection);
    engine.load(url);

    return app.exec();
}
