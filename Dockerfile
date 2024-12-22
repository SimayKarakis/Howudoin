FROM ubuntu:latest
LABEL authors="simay"

ENTRYPOINT ["top", "-b"]