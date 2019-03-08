# hello-k8s labb

## Syfte

Syftet med denna labb är att du ska få prova på Docker och Kubernetes i en lokalmiljö som är frikopplad från molnet.

Efter labben är klar så har du sjösatt din egna Docker container med en Hello World applikation i ett 
Kubernetes cluster med en nod.

Komponenterna som kommer att användas i denna labb är:

* Docker - Applikationen, Hello World, packas till en docker container.
* Kubectl - CLI:et till Kubernetes.
* Minikube - Ett verktyg för att köra Kubernetes kluster lokalt.
* Spring Boot - Applikationen vi kommer använda är skriven i Java och Spring Boot.

## Förberedelser

För att kunna genomföra labben behöver du:

* Java JDK 8
* Docker
* Kubectl
* Minikube
* Maven
* Git och Github
* Virtualbox

Om du får problem med att få upp din miljö, så ta hjälp av oss under labben.

Du även kan göra liknande variant av denna labb via Katacoda, om du får problem med att få upp miljön.

* https://kubernetes.io/docs/tutorials/hello-minikube/

Vi har haft problem med att få Minikube att fungera i HyperV, för Windows. Därför råder vi dig att använda Virtualbox och Docker Toolbox om du kör Windows, ty Docker CE kräver HyperV.

### Git och Github

För att få ut koden så behöver du clona repot, med git clone.
Om du vill ha en egen kopia på Github att labba med, så är det bara att forka. :)

### Java

Installera Java 8 JDK.

* https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

### Maven

Installera Maven i din miljö enligt dokumentationen. (Om du redan har maven via till exempel din IDE kan du skippa detta steg.)

* https://maven.apache.org/download.cgi

### Virtual Box

Installera Virtualbox i din miljö enligt dokumentationen. 
https://www.virtualbox.org/wiki/Downloads

### Docker

Installera Docker CE i din miljö enligt de officiella instruktionerna.

* https://docs.docker.com/install/

Om du använder Windows rekommenderar vi att du använder Docker Toolbox, då Docker CE använder HyperV i Windows.

* https://docs.docker.com/toolbox/toolbox_install_windows/

För att verifiera att Docker är korrekt installerat kan du köra Dockers egna Hello World container. Starta *Docker Quickstart Terminal*
och kör nedanstående kommando.

`docker run hello-world`

Då kommer Docker att hämta hello-world imagen från docker-hubs registry och exekvera den i din Docker miljö.

Utskriften du kommer få bör se ut så här:

```
Hello from Docker!
This message shows that your installation appears to be working correctly.

To generate this message, Docker took the following steps:
 1. The Docker client contacted the Docker daemon.
 2. The Docker daemon pulled the "hello-world" image from the Docker Hub.
    (amd64)
 3. The Docker daemon created a new container from that image which runs the
    executable that produces the output you are currently reading.
 4. The Docker daemon streamed that output to the Docker client, which sent it
    to your terminal.

To try something more ambitious, you can run an Ubuntu container with:
 $ docker run -it ubuntu bash

Share images, automate workflows, and more with a free Docker ID:
 https://hub.docker.com/

For more examples and ideas, visit:
 https://docs.docker.com/get-started/
```
### Kubectl

Installera Kubernetes CLI:et enligt dokumentationen: 

* https://kubernetes.io/docs/tasks/tools/install-kubectl/

### Minikube

Installera Minikube enligt dokumentationen.

* https://github.com/kubernetes/minikube

Om du tidigare har interagerat med ett Kubernetes kluster via din dator, så sätt minikube som nuvarande kontext:

`kubectl config use-context minikube`

För att verifiera att din minikube installation fungerar, så ska du köra en enkel test-applikation. Börja med att starta
*Microsoft Powershell* eller annan lämplig terminal.

Starta ditt Kubernetes kluster genom att köra:

`minikube start`

Du kan peka ut ett virtualiseringslager med optionen `--vm-driver` ifall den inte defaultar på Virtualbox:

`minikube start --vm-driver virtualbox`

Efter att ha startat minikube bör du då se följande konsol output:

```
o   minikube v0.34.1 on windows (amd64)
>   Creating virtualbox VM (CPUs=2, Memory=2048MB, Disk=20000MB) ...
-   "test" IP address is 192.168.99.102
o   Found network options:
    - NO_PROXY=192.168.99.100,192.168.99.101
-   Configuring Docker as the container runtime ...
-   Preparing Kubernetes environment ...
-   Pulling images required by Kubernetes v1.13.3 ...
-   Launching Kubernetes v1.13.3 using kubeadm ...
-   Configuring cluster permissions ...
-   Verifying component health .....
+   kubectl is now configured to use "test"
=   Done! Thank you for using minikube!
```

Du har nu skapat en virtuell maskin som du kan se i Virtualbox GUI.

För att sedan hämta en image med en applikation till ditt kluster och skapa en container från denna image, kör du följande kommando:

`kubectl run hello-minikube --image=k8s.gcr.io/echoserver:1.10 --port=8080`

Du bör då få följande utskrift:

`deployment.apps/hello-minikube created`

För att exponera denna container mot localhost så behöver du sedan köra följande kommando:

`kubectl expose deployment hello-minikube --type=NodePort`

Du bör då få följande utskrift:

`service/hello-minikube exposed`

För att se din pod där din applikation är sjösatt kör:

`kubectl get pods`

Du bör då se följande utskrift:

```
NAME                              READY   STATUS    RESTARTS   AGE
hello-minikube-6fd785d459-v8xj9   1/1     Running   0         2m25s
```

Var noga med att din pod har status `Running` innan du går vidare till nästa steg. Du kan köra kommandot ovan igen för att se om status har ändrats.

Om allting har gått bra hittills så bör du nu ha en körande pod som du kan curla med följande kommando:

`curl $(minikube service hello-minikube --url)`

Du bör då få följande utskrift:

```
StatusCode        : 200
StatusDescription : OK
Content           :

                    Hostname: hello-minikube-6fd785d459-v8xj9

                    Pod Information:
                        -no pod information available-

                    Server values:
                        server_version=nginx: 1.13.3 - lua: 10008

                    Request Information:
                        client_address=172.17.0....
RawContent        : HTTP/1.1 200 OK
                    Transfer-Encoding: chunked
                    Connection: keep-alive
                    Content-Type: text/plain
                    Date: Sun, 03 Mar 2019 19:41:48 GMT
                    Server: echoserver

                    Hostname: hello-minikube-6fd785d459-v8xj9

                    P...
Forms             : {}
Headers           : {[Transfer-Encoding, chunked], [Connection, keep-alive], [Content-Type, text/plain], [Date, Sun, 03 Mar 2019 19:41:48 GMT]...}
Images            : {}
InputFields       : {}
Links             : {}
ParsedHtml        : mshtml.HTMLDocumentClass
RawContentLength  : 517
```

Du är nu klar med uppsättningen av din labbmiljö! Hipp hipp! :)

## Labben

### Upplägg

Målet med labben är att du ska få bygga en egen Docker container med ett exempel Spring applikation. Sedan ska applikationen sjösättas i ditt Kubernetes cluster och göras åtkomstbar från din webbläsare.

### Bygg Spring applikationen

Vi börjar med att du ska få kompilera källkoden för vår exempelapplikation. Applikationen är skapad utifrån en template via Spring Initializr.

Börja med att öppna katalogen `Hello_R2M` i din miljö med till exempel IntelliJ.

Om du tittar runt i koden så kommer du se att applikationen skriver vi ut `Konnichiwa R2M` till `/` i webbläsaren. 
Du får gärna utöka ändra eller utöka koden om du vill göra en mera avancerad Spring Boot applikation.

Du behöver nu bygga applikationen med hjälp av Maven, du gör det genom att navigera till katalogen `hello-k8s\Hello_R2M` i din docker-terminal. I denna katalog skriver du sedan:

`mvn package`

En .jar fil kommer nu byggas, om allting går bra kommer du se en liknade utskrift som den nedan i din terminal:

```
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  7.384 s
[INFO] Finished at: 2019-03-06T09:14:49+01:00
[INFO] ------------------------------------------------------------------------
```

Om du navigerar till katalogen: `hello-k8s\Hello_R2M\target` så har du nu fått en .jar fil som heter `demo-0.0.1-SNAPSHOT.jar`.

Innan du går vidare så kolla att din .jar fil går att köra genom att köra kommandot:

`java -jar .\demo-0.0.1-SNAPSHOT.jar`

Du borde då se att Spring drar igång applikationen, i botten av utskriften bör du se:

```
2019-03-06 09:19:36.720  INFO 4060 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2019-03-06 09:19:36.724  INFO 4060 --- [           main] se.r2m.demo.DemoApplication              : Started DemoApplication in 2.46 seconds (JVM running for 2.878)
```

Öppna din webbläsare och gå till:

`http://localhost:8080`

Där bör du nu se utskriften nedan i din webbläsare.

`{"content":"Konnichiwa, R2M!"}`

Bryt körningen i din terminal genom att trycka, `Ctrl + C`.

Du kan nu gå vidare till nästa steg där du ska skapa en Docker container av applikationen du just byggde.

### Skapa din Docker container

För att bygga en Docker container så behöver du börja med att skapa en `Dockerfile`, som är en meta-datafil för hur Docker ska paketera din applikation till en container.

Om du fastnar så finns facit för detta steg i katalogen, `hello-k8s\Docker_facit`.

Börja med att skapa en katalog, `Docker` i repot, till exempel med:

`mkdir Docker` 

Kopiera sedan in .jar filen som du skapade i föregående steg till din Docker katalog med:

`cp .\Hello_R2M\target\demo-0.0.1-SNAPSHOT.jar .\Docker\`

I din Docker katalog skapar du sedan en fil som heter `Dockerfile`.

`touch .\Docker\Dockerfile`

Först börja du med välja en image från Dockerhub där du får OpenJDK 8 färdigt. ( https://hub.docker.com/_/openjdk )

Börja med att lägga till nedanstående rad i din Docker fil, där du definierar imagen som bas för containern.
 
`FROM openjdk:8-jdk-alpine`

Eftersom du vill kunna anropa din container via localhost på port 8080 så behöver containern exponera port 8080 utåt, detta åstadkommer du genom att definiera en portöppning med:

`EXPOSE 8080`

Sedan behöver du kopiera in .jar filen till Docker containern, lägg till följande i din Dockerfile:

`COPY demo-0.0.1-SNAPSHOT.jar .`

Till sist behöver du tala om för Docker att du vill exekvera .jar filen när containern startar. Det åstadkommer du genom att definiera ett kommando som containern ska köra med:

`CMD java -jar demo-0.0.1-SNAPSHOT.jar`

Nu ska din Docker fil innehålla följande:

``` 
FROM openjdk:8-jdk-alpine
EXPOSE 8080
COPY demo-0.0.1-SNAPSHOT.jar .
CMD java -jar demo-0.0.1-SNAPSHOT.jar
```

Det är nu dags att skapa din container! :D

Detta gör du genom att ställa dig i din Docker-katalog, du vill dessutom tagga din container med ett passande namn, det gör du med hjälp av `-t` optionen, så skriv följande kommando och kom ihåg att starta Docker innan:

`docker build -t konnichiwa-r2m:v1 .`

Utskriften du får kan se ut så här (om man har kört det förut, annars drar den hem några images först): 

```
Sending build context to Docker daemon  16.72MB
Step 1/4 : FROM openjdk:8-jdk-alpine
 ---> 792ff45a2a17
Step 2/4 : EXPOSE 8080
 ---> Using cache
 ---> 48148e215ac6
Step 3/4 : COPY demo-0.0.1-SNAPSHOT.jar .
 ---> daf490691f17
Step 4/4 : CMD java -jar demo-0.0.1-SNAPSHOT.jar
 ---> Running in f1bee9b76b3b
Removing intermediate container f1bee9b76b3b
 ---> 6e5cf2ad7d5e
Successfully built 6e5cf2ad7d5e
Successfully tagged konnichiwa-r2m:v1
```
Nu vill du köra din Docker container för att se att allting har gått bra hittills, det gör du med följande kommando:

`docker run -d --restart=unless-stopped -p 8080:8080 konnichiwa-r2m`

Testa att skriv `docker ps` så kommer du att se att du har startat en container, du bör få en utskrift som liknar den nedan.

```
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                    NAMES
a6551a32b5f5        konnichiwa-r2m      "/bin/sh -c 'java -j…"   10 minutes ago      Up 10 minutes       0.0.0.0:8080->8080/tcp   keen_antonelli
```

Du behöver ta reda vilken IP adress som Docker har använt för att exponera din container så skriv:

`docker-machine ls`

Du bör få en utskrift som liknar denna, 

```
NAME             ACTIVE   DRIVER       STATE     URL                         SWARM   DOCKER     ERRORS
default          *        virtualbox   Running   tcp://192.168.99.101:2376           v18.09.3
```

Lägg URL:en på minnet och öppna din webbläsare och surfa till URL på port 8080, i detta fall:

`http://192.168.99.101:8080/`

Du bör nu se en bekant utskrift från vår tidigare javaapplikation.

Innan du går vidare så bör du stoppa containern, byt ut CONTAINER_ID mot din körande containers id som du fick från `docker ps`.

`docker stop CONTAINER_ID`

Grattis du har nu byggt din egen container och du ska nu få sjösätta den i minikube!

### Minikube

#### Starta minikube

Gå till ditt andra terminalfönster (PowerShell eller motsvarande - inte Docker Quickstart). Börja med att starta minikube (om du inte redan gjort det) med:

`minikube start`

Om du redan har skapat ett kluster från förberedelserna innan så kommer minikube starta upp det klustret.
Du bör se något liknande följande terminal utskrift:

```
o   minikube v0.34.1 on windows (amd64)
i   Tip: Use 'minikube start -p <name>' to create a new cluster, or 'minikube delete' to delete this one.
:   Restarting existing virtualbox VM for "minikube" ...
:   Waiting for SSH access ...
-   "minikube" IP address is 192.168.99.100
o   Found network options:
    - NO_PROXY=192.168.99.100,192.168.99.101
-   Configuring Docker as the container runtime ...
-   Preparing Kubernetes environment ...
-   Pulling images required by Kubernetes v1.13.3 ...
:   Relaunching Kubernetes v1.13.3 using kubeadm ...
:   Waiting for kube-proxy to come back up ...
-   Verifying component health ......
+   kubectl is now configured to use "minikube"
=   Done! Thank you for using minikube!
```

#### Sjösätt din container

Du ska nu sjösätta din container i ditt Kubernetes kluster. Vi kommer använda oss av en genväg här för att förenkla labben. I moln tjänster så som Azure så laddar man upp sina containers till ett container registry som versionshanterar dina containrar. Du kan skapa ditt egna container registry lokalt men nu kommer vi använda Minikubes Docker daemon för att förenkla uppsättningen.

Börja med att skriva följande kommando i din terminal:

`minikube docker-env`

I botten av utskriften bör du nu få ett eller flera kommandon som du kan skriva i ditt shell för att återanvända daemon. I Windows Powershell är raden: `& minikube docker-env | Invoke-Expression` men den kan se olika ut beroende på vilket operativsystem du kör. 

Nu har vi alltså pekat om docker-kommandona till att använda Minikubes container registry. För att kunna hitta den image vi skall starta måste du bygga den igen. Navigera till docker-katalogen och kör

`docker build -t konnichiwa-r2m:v1 .`

För att sätta ingång din container så kör nedanstående kommando. Kommandot gör en deployment ska göras med namnet `konnichiwa-r2m` från din image, där du sedan exponerar port 8080 på det interna kluster IP:et. 

`kubectl run konnichiwa-r2m --image=konnichiwa-r2m:v1 --image-pull-policy=Never --port=8080`

Sedan behöver du sätta igång en service som exponerar clustret interna port till din localhost.

`kubectl.exe expose deployment konnichiwa-r2m --type=NodePort`

Du bör nu kunna curla din pod genom att skriva:

`curl $(minikube service konnichiwa-r2m --url)`

Du bör se följande utskrift:

```
StatusCode        : 200
StatusDescription :
Content           : {"content":"Konnichiwa, R2M!"}
RawContent        : HTTP/1.1 200
                    Transfer-Encoding: chunked
                    Content-Type: application/json;charset=UTF-8
                    Date: Thu, 07 Mar 2019 19:05:51 GMT

                    {"content":"Konnichiwa, R2M!"}
Forms             : {}
Headers           : {[Transfer-Encoding, chunked], [Content-Type, application/json;charset=UTF-8], [Date, Thu, 07 Mar 2019 19:05:51 GMT]}
Images            : {}
InputFields       : {}
Links             : {}
ParsedHtml        : mshtml.HTMLDocumentClass
RawContentLength  : 30
```

#### Experimentera med ditt kluster

För att interagera med din container på din pod så kan du starta ett shell mot den.
Börja med att skriva, `kubectl get pods` för att få se vad din pod heter.
Du bör få följande utskrift bortsett från att din pod kommer ha en annan kombination på slutet.

```
NAME                              READY   STATUS    RESTARTS   AGE
konnichiwa-r2m-65d7c94945-vscbf   1/1     Running   0          8h
```
Ersätt sedan podens namn med din kombination och kör nedanstående kommando.

 `kubectl exec -it konnichiwa-r2m-DIN_KOMBINATION /bin/sh`

Som du ser har du nu ett Shell på din pod, testa att köra några valfria kommandon och utforska poden.

Ett annat intressant experiment du kan pröva är att ta bort din pod och faktiskt se att Kubernetes kommer skapa om poden. Testa att skriva:

`kubectl delete pod konnichiwa-r2m-DIN_KOMBINATION `

Du kan se att poden tas bort och skapas om du kör: `kubectl get pods`

Eftersom du har definierat en deployment med en körande container så kommer Kubernetes dra igång container igen. Du kan se information om din deployment om du kör: `kubectl get deployments`

### Clean up

Du är nu klar med labben, experimentera vidare om du vill eller börjar riv miljön enligt följande instruktioner.

Börja med att ta bort din service:

`kubectl delete service konnichiwa-r2m`

Plocka sedan ner din deployment med:

`kubectl delete deployment konnichiwa-r2m`

Stoppa minikube:

`minikube stop`

Till sist så raderar du ditt minikube kluster:

`minikube delete`

Du är nu klar med labben! Hipp hipp! :)

Tack så mycket för att du tog dig tid och om du har tankar kring labben så hör av dig.
All konstruktiv kritik och feedback mottages varmt.

## Further Reading

Nedan följer en fördjupad tutorial för Kubernetes där du får använda Katacoda för att labba i din webbläsare.

* https://kubernetes.io/docs/tutorials/kubernetes-basics/

Nedan följer en länge beskrivning med exempel för Kubernetes:

* https://medium.freecodecamp.org/learn-kubernetes-in-under-3-hours-a-detailed-guide-to-orchestrating-containers-114ff420e882

Nedan följer en tutorial för hur du sjösätter en Docker container med hjälp av Kubernetes i Azure.

* https://docs.microsoft.com/en-us/azure/aks/tutorial-kubernetes-prepare-app