# AI-forexPredict
A library aimed at providing utilities for computing indicators data and also building a prediction  ML model and other

### Dependencies
The library is developed with the help of gradle tool and JDK```version 18```
```
Gradle: org.apiguardian:apiguardian-api:1.1.2
Gradle: org.glassfish.tyrus.bundles:tyrus-standalone-client:1.9
Gradle: org.json:json:20230227
Gradle: org.junit.jupiter:junit-jupiter-api:5.9.1
Gradle: org.junit.jupiter:junit-jupiter-engine:5.9.1
Gradle: org.junit.jupiter:junit-jupiter-params:5.9.1
Gradle: org.junit.jupiter:junit-jupiter:5.9.1
Gradle: org.junit.platform:junit-platform-commons:1.9.1
Gradle: org.junit.platform:junit-platform-engine:1.9.1
Gradle: org.opentest4j:opentest4j:1.2.0
```
### Packages included so far.
#### Data Collection and Pre-processing
For collection and cleaning of forex data and technical indicators from various sources. In here there is usage of a default API to fetch forex or instrument data

#### Feature Engineering and Selection
For generating and selecting event-driven features indicating a change of trend in direction. This involves implementation of some of the technical indicators relevant for trend prediction among which include;
```Trend```, ```Oscillators``` and ```Volume``` indicators.

### Technical Indicators
This is partial development and so does not contain all indicators as the other has be added in the next cycle of development
#### Trend
<ul>
<li>Exponential Moving Average</li>
<li>Simple Moving Average</li>
<li>Smoothed Moving Average</li>
<li>Linear Weighted Moving Average</li>
<li>Standard Deviation</li>
</ul>

#### Oscillator
<ul>
<li>Relative Strength Index</li>
<li>Commodity Channel Index</li>
<li>Average True Index</li>
<li>Stochastic Oscillator</li>
</ul>

#### Volume
> Under Development! <strong style="color: yellow; padding: 3px; background-color: green; border-radius: 6px;">Pending</strong>

### ML Models
#### Support Vector Machine (SVM)
<ul>
<li>CPU - Currently implemented to run on the CPU</li>
<li>GPU - <strong style="color: yellow; padding: 3px; background-color: green; border-radius: 6px;">Pending</strong></li>
</ul>

#### Long Short-Term(LSTM) Memory
<ul>
<li>CPU - <strong style="color: yellow; padding: 3px; background-color: green; border-radius: 6px;">Pending</strong></li>
<li>GPU - <strong style="color: yellow; padding: 3px; background-color: green; border-radius: 6px;">Pending</strong></li>
</ul>