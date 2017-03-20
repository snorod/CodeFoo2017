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

        <Text style={styles.welcome}>
          VIDEOS
        </Text>
        <ListViewArticles name='articles' />
        <Text style={styles.instructions}>
          To get started, edit index.android.js
        </Text>
        <Text style={styles.instructions}>
          Double tap R on your keyboard to reload,{'\n'}
          Shake or press menu button for dev menu
        </Text>
      </View>
    );
  }
}

class ListViewArticles extends Component {
  // Initialize the hardcoded data
  constructor(props) {
    super(props);
    const ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
    this.state = {
      dataSource: ds.cloneWithRows([
        'John', 'Joel', 'James', 'Jimmy', 'Jackson', 'Jillian', 'Julie', 'Devin'
      ])
    };
  }
  render() {
    return (
      <View style={{flex: 1, paddingTop: 22}}>
        <ListView
        var myAPIcall = fetch('http://ign-apis.herokuapp.com/articles?startIndex=30\u0026count=5');
          dataSource={this.state.dataSource}
          renderRow={(rowData) => <Text>{rowData}</Text>}
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

class SearchButton extends Component {
  render() {
    let pic = {
      uri: 'http://mevicer.ge/gold-skins/default/images/search.png'
    };
    return (
      <Image source={pic} style={{width: 15, height: 15}}/>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    //justifyContent: 'center',
    //alignItems: 'center',
    //backgroundColor: '#F5FCFF',
    backgroundColor: '#F5FCFF',
  },
  headerBar: {
    flexDirection: 'row',
    height: 40,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#000000',
    //height: 10,
    //width: 10,
    //flexDirection: 'stretch',
  },
  welcome: {
    fontSize: 30,
    textAlign: 'center',
    fontWeight: 'bold',
    //fontFamily: 'Times New Roman',
    margin: 10,
    color: '#9E9E9E',
  },
  instructions: {
    textAlign: 'center',
    //color: '#333333',
    color: '#BDBDBD',
    marginBottom: 5,
  },
});

AppRegistry.registerComponent('MobileApp', () => MobileApp);
