# Start from a base image with necessary tools (e.g., Java, C++)
FROM mcr.microsoft.com/devcontainers/base:ubuntu

ARG WPILIB_VERSION=2026.2.1

# Install dependencies and download the WPILib installer
RUN apt-get update && export DEBIAN_FRONTEND=noninteractive \
    && apt-get -y install --no-install-recommends wget unzip gnupg2 openjdk-17-jdk curl ca-certificates \
    && apt-get clean && rm -rf /var/lib/apt/lists/*

ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64

# Download and extract the latest WPILib installer
# NOTE: Update the URL and version for the LATEST release
WORKDIR /tmp/wpilib_installer
RUN set -eux; \
    wget "https://github.com/wpilibsuite/allwpilib/releases/download/v2026.2.1/WPILib_Linux-2026.2.1.tar.gz" -O wpilib_archive; \
    mkdir -p /root/wpilib; \
    tar -zxvf wpilib_archive -C /root/wpilib --strip-components=1

# Clean up
RUN rm wpilib_archive


