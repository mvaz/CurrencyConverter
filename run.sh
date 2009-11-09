#1/bin/sh

# use JDK 1.6
JAVA=/System/Library/Frameworks/JavaVM.framework/Versions/1.6/Home/bin/java

# launches the main program
CLASSPATH=$CLASSPATH:`pwd`
CLASSPATH=$CLASSPATH:`pwd`/jgrapht-jdk1.6.jar

# 
#$JAVA -classpath $CLASSPATH net/mvaz/CurrencyConverter/TestProgram
$JAVA -classpath $CLASSPATH net/mvaz/CurrencyConverter/ConvertCurrency
