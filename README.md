# Prerequis
BCM4

# installation et lancement

```
Compilation BCM4_CPS -> bcm.jar
cd BCM4_CPS/
ant dist

Installatin
cp BCM4_CPS/bcm.jar mourad-alpha/lib
cd mourad-alpha/
ant dist

exemple de lancement de brokers3
./start-cyclicbarrier-performance-3-broker&
./start-gregistry-performance-10-broker&
./start-performance-3-broker-all 
```
