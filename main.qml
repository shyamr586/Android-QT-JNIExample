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
        MouseArea {anchors.fill: parent; onClicked: {myPicker.openFile(); }}
    }

    ///data/user/0/com.example.march31videoplayer/cache/primary%3ADCIM%2FCamera%2F007a8ffa9b2d380ce17474412bb36dd1.mp4
    //file:/data/user/0/com.example.march31videoplayer/cache/primary%253ADCIM%252FCamera%252F007a8ffa9b2d380ce17474412bb36dd1.mp4
    Text {
        id: myText
        anchors.bottom: parent.bottom
        anchors.margins: 50
        width: parent.width
        color: "black"
        wrapMode: Text.WordWrap
        text: "The path is: "+myPicker.filepath;
    }
    Rectangle {
        id: pinkRect
        anchors.top: myText.bottom
        color: "pink"
        height: 30; width: 100
        MouseArea {anchors.fill: parent; onClicked: {changePlaybackState1()}}
    }

    Rectangle {
        id: greenRect
        anchors.top: myText.bottom
        anchors.left: pinkRect.right
        color: "green"
        height: 30; width: 100
        MouseArea {anchors.fill: parent; onClicked: {changePlaybackState2()}}
    }

    MediaPlayer {
        id: myPlayer
        source: "content://com.android.providers.media.documents/document/video%3A31640"
        audioOutput: AudioOutput{}
        videoOutput: myVid
    }

    //just set a function to true or false when its playing over here and in cpp side just set the sleep property
    //of android

    VideoOutput {
        id: myVid
        height: 100; width: 100
    }

    function changePlaybackState1(){
        myPicker.setScreenLock(false);
        //myPicker.toggleAutoLock(true);
//        if (myPlayer.playbackState === MediaPlayer.PlayingState){
//            //myPlayer.pause();
//            myPicker.toggleAutoLock(false);
//        } else if (myPlayer.playbackState === MediaPlayer.PausedState){
//            //myPlayer.play();
//            myPicker.toggleAutoLock(true);
//        }
    }

    function changePlaybackState2(){
        myPicker.setScreenLock(true);
    }
}
