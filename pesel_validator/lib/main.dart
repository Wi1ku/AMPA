import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

const PESEL_LENGTH = 11;

void main() {
  runApp(App());
}

class App extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "Pesel Validator",
      theme: ThemeData(
        // This is the theme of your application.
        //
        // Try running your application with "flutter run". You'll see the
        // application has a blue toolbar. Then, without quitting the app, try
        // changing the primarySwatch below to Colors.green and then invoke
        // "hot reload" (press "r" in the console where you ran "flutter run",
        // or simply save your changes to "hot reload" in a Flutter IDE).
        // Notice that the counter didn't reset back to zero; the application
        // is not restarted.
        primarySwatch: Colors.blue,
        // This makes the visual density adapt to the platform that you run
        // the app on. For desktop platforms, the controls will be smaller and
        // closer together (more dense) than on mobile platforms.
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: MainPage(title: "Pesel Validator"),
      routes: <String, WidgetBuilder> {
        '/main': (BuildContext context) => MainPage(title: "Pesel Validator")
      },
    );
  }
}
class MainPage extends StatelessWidget {
  MainPage({Key key, this.title}) : super(key: key);
  final String title;
  @override
  Widget build(BuildContext context) {

    return MaterialApp(
      title: this.title,
      home: Scaffold(
        appBar: AppBar(
          title: Text(this.title),
        ),
        body: PeselForm(),
      ),
    );
  }
}

class PeselForm extends StatefulWidget {
  @override
  _PeselFormState createState() => _PeselFormState();
}

class _PeselFormState extends State<PeselForm> {
  final _peselcontroller = TextEditingController();
  Widget build(BuildContext context) {
    final _formKey = GlobalKey<FormState>();
    return Form(
        key: _formKey,
        child: Column(
            children: <Widget>[
              TextFormField(
                validator: (value) {
                  if (value.isEmpty) {
                    return 'Please enter your pesel';
                  }
                  else if (value.length != PESEL_LENGTH){
                    return 'Pesel must be exactly 11 charaters long';
                  }
                  return null;
                },
                keyboardType: TextInputType.number,
                  inputFormatters: <TextInputFormatter>[
                  FilteringTextInputFormatter.digitsOnly],
                  controller: _peselcontroller,
              ),
              ElevatedButton(
                onPressed: () {
                  // Validate returns true if the form is valid, otherwise false.
                  if (_formKey.currentState.validate()) {
                    // If the form is valid, display a snackbar. In the real world,
                    // you'd often call a server or save the information in a database.

                    Navigator.push(context,  MaterialPageRoute(
                      builder: (context) => ResultPage(this._peselcontroller.value.text),
                    )
                    );
                  }
                },
                child: Text('Submit'),
              )
            ]
        )
    );
  }
}



class ResultPage extends StatelessWidget {
  ResultPage(this.pesel) : super();
  final String pesel;
  @override
  Widget build(BuildContext context) {
    final appTitle = 'Result';

    return MaterialApp(
      title: appTitle,
      home: Scaffold(
        appBar: AppBar(
          title: Text(appTitle),
        ),
        body: Center(
          child: Text(pesel),
        ),
      ),
    );
  }
}



