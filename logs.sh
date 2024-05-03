#!/bin/bash

# Check if a container name or ID is provided
if [ $# -eq 0 ]; then
  echo "Usage: $0 <container_name_or_id>"
  exit 1
fi

# Get the container name or ID from command line arguments
container="$1"

# Set the path to the log file
log_file="applogs.txt"

# Save container logs to the specified file
docker logs "$container" > "$log_file"

# Check if the log extraction was successful
if [ $? -eq 0 ]; then
  echo "Container logs saved to $log_file"
else
  echo "Error saving container logs"
fi