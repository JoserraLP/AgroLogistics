# start by pulling the python image
FROM python:3.10

# copy folder to image
COPY . /app

# Add docker-compose-wait tool -------------------
ENV WAIT_VERSION 2.7.2
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/$WAIT_VERSION/wait /wait
RUN chmod +x /wait

# install the dependencies and packages in the requirements file
RUN pip install -r /app/requirements.txt