#ifndef FILEPICKER_H
#define FILEPICKER_H

#include <QObject>

class FilePicker : public QObject
{
    Q_OBJECT
    Q_PROPERTY(QString filepath READ filepath WRITE setFilepath NOTIFY filepathChanged)
public:
    explicit FilePicker(QObject *parent = nullptr);
    QString filepath();
signals:
    void filepathChanged();
public slots:
    void setFilepath(QString);
    void openFile();
private:
    QString m_filepath;
};

#endif // FILEPICKER_H
