#include "filepicker.h"
#include <QJniObject>
#include <QGuiApplication>
#include <QDebug>

QJniObject javaClass = QNativeInterface::QAndroidApplication::context();

extern "C" JNIEXPORT void JNICALL
Java_com_example_march31_MainActivity_filePathReceived(JNIEnv* env, jclass, jlong filepickerpointer, jstring filepath) {
    //qDebug() << "File path is " << QString(env->GetStringUTFChars(filepath,0));
    QString qstringFilePath= QString(env->GetStringUTFChars(filepath,0));
    reinterpret_cast<FilePicker*>(filepickerpointer)->setFilepath(qstringFilePath);

    qDebug() << "File path";
}

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

    //FilePicker filePicker;
    //qDebug() << "Type of the pointer is: " << typeid(*pointerLong).name();
    javaClass.callMethod<void>("openFile","(J)V", (long long)(FilePicker*)this);
}
