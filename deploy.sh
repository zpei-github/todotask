git clone "https://github.com/zpyyds666666/todotask.git"

cd ./todotask

docker volume create --name ssadmin-maven-repo


docker run -it --rm --name ssadmin-maven \
    -v ssadmin-maven-repo:/root/.m2 \
    -v "$PWD/src/springboot-server":/usr/src/mymaven \
    -w /usr/src/mymaven \
    maven:3.8.4-jdk-8 mvn clean install package -e -Dmaven.test.skip=true
	

docker-compos up -d
