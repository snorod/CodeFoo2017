import React, { Component } from 'react';
import {
  AppRegistry,
  Image,
  StyleSheet,
  Text,
  View,
  ListView,
  TouchableHighlight
} from 'react-native';

export default class MobileApp extends Component {
  render() {
    return (
      <View style={styles.container}>
        <View style={styles.headerBar}>
          <View style={{flex: 1, margin: 10, justifyContent: 'flex-start', backgroundColor: '#000000'}}>
            <MenuButton name='MenuButton' />
          </View>
          <View style={{flex: 1, backgroundColor: '#000000'}}>
            <Logo name='ign' />
          </View>
          <View style={{justifyContent: 'flex-end', margin: 10, backgroundColor: '#000000'}}>
            <SavedListButton name='savedListButton' />
          </View>
        </View>


        <ListViewArticles name='articles' />
        <Text style={styles.welcome}>
          VIDEOS
        </Text>
        <ListViewVideos name='videos' />
        <ListViewArticles name='articles' />
        <Text style={styles.welcome}>
          VIDEOS
        </Text>
        <ListViewVideos name='videos' />
        <ListViewArticles name='articles' />
        <Text style={styles.instructions}>
          created by snorod
        </Text>
      </View>
    );
  }
}


class ListViewArticles extends Component {
  constructor(props) {
    super(props);
    const ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});

    this.state = {
      dataSource: ds.cloneWithRows([
        'article1', 'article2'
      ])
    };
  }
  render() {
    return (
      <View style={{flex: 1, paddingTop: 22}}>
        <ListView
          dataSource={this.state.dataSource}
          renderRow={(rowData) => <Text style={styles.instructions}>{rowData}</Text>}

        />
      </View>
    );
  }
}

class ListViewVideos extends Component {
  constructor(props) {
    super(props);
    const ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});

    this.state = {
      dataSource: ds.cloneWithRows([
        'video1', 'video2', 'video3', 'video4', 'video5'
      ])
    };
  }
  render() {
    return (
      <View style={{flex: 1, paddingTop: 22}}>
        <ListView
          dataSource={this.state.dataSource}
          renderRow={(rowData) => <Text style={styles.instructions}>{rowData}</Text>}

        />
      </View>
    );
  }
}

class MenuButton extends Component {
  _onPressButton() {
    console.log("You tapped the button!");
  }

  render() {
    let pic = {
      uri: 'http://expulsions.heraldtribune.com/img/burger.png'
    };
    return (
      <TouchableHighlight onPress={this._onPressButton}>
        <Image source={pic} style={{width: 20, height: 15}}/>
      </TouchableHighlight>
    );
  }
}

class Logo extends Component {
  render() {
    let pic = {
      uri: 'http://rolocule.com/wp-content/uploads/ign20101.jpg'
    };
    return (
      <Image source={pic} style={{width: 70, height: 21}}/>
    );
  }
}

class SavedListButton extends Component {
  _onPressButton() {
    console.log("You tapped the button!");
  }

  render() {
    let pic = {
      uri: 'https://www.materialui.co/materialIcons/action/history_white_192x192.png'
    };
    return (
      <TouchableHighlight onPress={this._onPressButton}>
        <Image source={pic} style={{width: 25, height: 25}}/>
      </TouchableHighlight>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,

    backgroundColor: '#000000',
  },
  headerBar: {
    flexDirection: 'row',
    height: 40,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#000000',

  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    fontWeight: 'bold',

    margin: 10,
    color: '#9E9E9E',
  },
  instructions: {
    textAlign: 'center',

    color: '#BDBDBD',
    marginBottom: 5,
  },
});

AppRegistry.registerComponent('MobileApp', () => MobileApp);
