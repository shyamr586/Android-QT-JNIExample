#include "filepicker.h"
#include <QJniObject>
#include <QJniEnvironment>
#include <QGuiApplication>
#include <QDebug>
#include <QFileInfo>

QJniObject javaClass = QNativeInterface::QAndroidApplication::context();

extern "C" JNIEXPORT void JNICALL
Java_com_example_march31videoplayer_MainActivity_filePathReceived(JNIEnv* env, jclass, jlong filepickerpointer, jstring filepath) {
    //qDebug() << "File path is " << QString(env->GetStringUTFChars(filepath,0));
    QString qstringFilePath= QString(env->GetStringUTFChars(filepath,0));
    reinterpret_cast<FilePicker*>(filepickerpointer)->setFilepath(qstringFilePath);
    //QString size = QFileInfo(qstringFilePath);

    qint64 size = QFileInfo(qstringFilePath).size();

    QString returnedPath = QFileInfo(qstringFilePath).filePath();
    //qDebug() << "The size of the file is: "+size;
    qDebug() << "File path on extern is "<< qstringFilePath;
    qDebug() << "File path using QFile is "<< returnedPath;
    qDebug() << "Size of the path is " <<size;
}

///document/primary:Download/Sample_vid.mp4
///document/primary:Download/Sample_vid.mp4
///file:/document/primary:Download/Sample_vid.mp4
///content://com.android.externalstorage.documents/document/primary%3ADownload%2FSample_vid.mp4

FilePicker::FilePicker(QObject *parent)
    : QObject{parent}
{

}

QString FilePicker::filepath(){
    return m_filepath;
}

void FilePicker::setFilepath(QString newPath){
    if (m_filepath!=newPath){
        m_filepath = newPath;
        Q_EMIT filepathChanged();
    }
}

void FilePicker::openFile(){

    ///content:/com.android.externalstorage.documents/document/primary%3ADCIM%2FCamera%2F007a8ffa9b2d380ce17474412bb36dd1.mp4
    ///content://com.android.externalstorage.documents/document/primary%3ADCIM%2FCamera%2F007a8ffa9b2d380ce17474412bb36dd1.mp4

    //FilePicker filePicker;
    //qDebug() << "Type of the pointer is: " << typeid(*pointerLong).name();
    javaClass.callMethod<void>("openFile","(J)V", (long long)(FilePicker*)this);


}

void FilePicker::setScreenOn(bool isOn){
    qDebug() << "Screen lock is " << isOn;
    javaClass.callMethod<void>("setScreenOn","(Z)V", isOn);
}
