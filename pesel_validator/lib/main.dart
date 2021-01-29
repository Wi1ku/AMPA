import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:pesel_validator/peselCompute.dart';


const PESEL_LENGTH = 11;

void main() {
  runApp(App());
}

class App extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "Pesel Validator",
      theme: ThemeData(
        primarySwatch: Colors.blue,
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
              Padding(
                padding: const EdgeInsets.all(8.0),
                child: Text("Enter your PESEL number below: ", style: DefaultTextStyle.of(context).style.apply(fontSizeFactor: 1.5)),
              ),
              Padding(
                padding: const EdgeInsets.all(40.0),
                child: TextFormField(
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
              ),
              ElevatedButton(
                onPressed: () {
                  if (_formKey.currentState.validate()) {
                    Navigator.push(context,  MaterialPageRoute(
                      builder: (context) => ResultPage(this._peselcontroller.value.text),
                    )
                    );
                  }
                },
                child: Text('Submit'),
              )
            ],
          mainAxisSize: MainAxisSize.max,
          mainAxisAlignment: MainAxisAlignment.center,
        )
    );
  }
}



class ResultPage extends StatelessWidget {
  ResultPage(this.pesel) : super();
  final String pesel;

  get peselInfo => getPeselInfo(pesel);
  @override
  Widget build(BuildContext context) {
    final appTitle = 'Result';
    return MaterialApp(
      title: appTitle,
        theme: ThemeData(
          textTheme: TextTheme(
            headline1: TextStyle(
              fontSize: 22.0
              ),
              bodyText1: TextStyle(
              fontSize: 16.0
              ),
            )
        ),
    home: Scaffold(
        appBar: AppBar(
          title: Text(appTitle),
        ),
        body:
        Container(
          child: Column(
              children: <Widget>[
                Padding(
                  padding: const EdgeInsets.all(16.0),
                  child: Text("Result for given number: ", textScaleFactor: 2.0,),
                ),
              Padding(
                padding: const EdgeInsets.all(8.0),
                child: Text("PESEL number: ${pesel}"),
              ),
              Padding(
                padding: const EdgeInsets.all(8.0),
                child: Text("Control sum value: ${peselInfo['controlSumisVaild'] ? 'valid' : 'invalid'}"),
              ),
              Padding(
                padding: const EdgeInsets.all(8.0),
                child: Text("Gender: ${peselInfo['gender']}"),
              ),
              Padding(
                padding: const EdgeInsets.all(8.0),
                child: Text("Date of birth: ${peselInfo['birthDate']}"),
              )
            ],
            crossAxisAlignment: CrossAxisAlignment.start,
            mainAxisSize: MainAxisSize.max,
          ),
        ),
      ),
    );
  }
}



