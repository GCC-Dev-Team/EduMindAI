# 使用基于 Ubuntu 20.04 的官方镜像
FROM ubuntu:20.04

# 更新apt源并安装PostgreSQL、Redis、MongoDB和Minio
RUN apt-get update && \
    DEBIAN_FRONTEND="noninteractive" apt-get install -y wget gnupg2 lsb-release && \
    echo "deb http://apt.postgresql.org/pub/repos/apt $(lsb_release -cs)-pgdg main" | tee /etc/apt/sources.list.d/pgdg.list && \
    wget --quiet -O - https://www.postgresql.org/media/keys/ACCC4CF8.asc | apt-key add - && \
    apt-get update && \
    DEBIAN_FRONTEND="noninteractive" apt-get install -y postgresql-16 redis-server mongodb minio && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# 设置PostgreSQL的密码
RUN service postgresql start && \
    sudo -u postgres psql -c "ALTER USER postgres PASSWORD 'abC123456@';"

# 设置Redis密码
RUN sed -i 's/^requirepass.*$/requirepass abC123456@/' /etc/redis/redis.conf

# 开放PostgreSQL、Redis和Minio远程端口
EXPOSE 5432 6379 9000

# 设置MongoDB用户名和密码
RUN mongod --fork --logpath /var/log/mongodb.log && \
    mongo admin --eval 'db.createUser({user: "xiaoli", pwd: "abC123456@", roles: ["userAdminAnyDatabase"]});' && \
    mongod --shutdown

# 拷贝初始化SQL脚本到容器中
COPY init.sql /docker-entrypoint-initdb.d/init.sql

# 初始化PostgreSQL数据库
RUN service postgresql start && \
    sudo -u postgres psql -f /docker-entrypoint-initdb.d/init.sql && \
    service postgresql stop

# 创建工作目录
WORKDIR /app

# 默认命令为启动PostgreSQL、Redis、MongoDB和Minio服务
CMD ["sh", "-c", "service postgresql start && redis-server /etc/redis/redis.conf && mongod --bind_ip_all && minio server /data"]
