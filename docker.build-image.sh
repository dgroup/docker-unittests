#!/bin/bash
echo ""
if [ -z "$1" ]
    then
        echo "Usage: $(basename $0) [DOCKER_IMAGE_NAME] [PROJECT_HOME] [PATH_TO_DOCKERFILE]"
        echo -e "  \e[0;33mPROJECT_HOME\e[0;37m \t\tDirectory for files which should be used during image build process"
        echo -e "  \e[0;33mPATH_TO_DOCKERFILE\e[0;37m \tPath to Dockerfile location (src/docker/Dockerfile)"
        echo ""
        echo -e "  $(basename $0) \e[0;33mgradle:3.4 . src/docker/Dockerfile\e[0;37m"
        echo ""
        exit 1
    else
        docker_image=$1
fi
if [ -z "$2" ]
  then
    echo "Kindly ask you to specify the [PROJECT_ROOT] parameter"
    echo -e "  $(basename $0) gradle:3.4 \e[0;33m.\e[0;37m"
    echo ""
    exit 1;
  else
    project_root=$2
fi
if [ -z "$3" ]
  then
    echo "Kindly ask you to specify the [PATH_TO_DOCKERFILE] parameter"
    echo -e "  $(basename $0) gradle:3.4 . \e[0;33msrc/docker/Dockerfile\e[0;37m"
    echo ""
    exit 1;
  else
    docker_file=$3
fi

echo "Docker image name"
echo "  $docker_image"
echo "Project root directory"
echo "  $project_root"
echo "Dockerfile location"
echo "  $docker_file"
echo ""
docker build -t $docker_image $project_root -f $docker_file --no-cache
docker inspect $docker_image
echo ""