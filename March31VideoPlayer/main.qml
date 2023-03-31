import QtQuick 2.15
import QtMultimedia
import QtQuick.Window 2.15
import FilePicker 1.0

Window {
    width: 640
    height: 480
    visible: true
    title: qsTr("Hello World")

    FilePicker {
        id: myPicker
    }

    Rectangle {
        color: "black"
        height: 30; width: 100
        MouseArea {anchors.fill: parent; onClicked: {myPicker.openFile(); myPlayer.source = "file:///"+myPicker.filepath}}
    }


    Text {
        id: myText
        anchors.bottom: parent.bottom
        anchors.margins: 50
        color: "yellow"
        text: "The path is: "+myPicker.filepath;
    }
    Rectangle {
        anchors.top: myText.bottom
        color: "pink"
        height: 30; width: 100
        MouseArea {anchors.fill: parent; onClicked: {myPlayer.play()}}
    }

    MediaPlayer {
        id: myPlayer

        audioOutput: AudioOutput{}
        videoOutput: myVid
    }

    //just set a function to true or false when its playing over here and in cpp side just set the sleep property
    //of android

    VideoOutput {
        id: myVid
        height: 100; width: 100
    }
}
