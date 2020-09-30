/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React, {useEffect, useState} from 'react';
import {
  SafeAreaView,
  StyleSheet,
  View,
  Button,
  StatusBar,
  NativeModules,
  NativeEventEmitter,
  Alert,
} from 'react-native';

const {Torch} = NativeModules;
const TorchEvents = new NativeEventEmitter(Torch);

const App = () => {
  const [isOn, setOn] = useState(false);
  useEffect(() => {
    TorchEvents.addListener('OnChange', (value) => {
      setOn(value);
      Alert.alert(
        'OnChange From Native Side',
        value.toString(),
        [{text: 'OK', onPress: null}],
        {cancelable: false},
      );
    });
    return TorchEvents.removeListener();
  }, []);

  const backgroundColor = {backgroundColor: isOn ? '#ffff88' : 'black'};
  return (
    <>
      <StatusBar barStyle="dark-content" />
      <SafeAreaView>
        <View style={[styles.container, backgroundColor]}>
          <Button
            title="ON"
            onPress={() => {
              Torch.toggleTorch(true);
            }}
            style={styles.btnOn}
          />
          <Button
            title="OFF"
            onPress={() => {
              Torch.toggleTorch(false);
            }}
          />
        </View>
      </SafeAreaView>
    </>
  );
};

const styles = StyleSheet.create({
  container: {
    height: '100%',
    justifyContent: 'center',
    alignItems: 'center',
  },
  btnOn: {
    marginBottom: 15,
  },
});

export default App;
