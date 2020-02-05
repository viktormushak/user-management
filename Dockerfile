FROM rtfpessoa/ubuntu-jdk8

ADD /wait.sh wait.sh
ADD /target/user-management-0.0.1-SNAPSHOT.jar vertex-user-management.jar

CMD ["./wait.sh"]
