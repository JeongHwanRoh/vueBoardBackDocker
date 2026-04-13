🚀 Docker Hub를 활용한 프로젝트 배포 및 이전 가이드
본 가이드는 Windows(개발 환경)에서 수정된 Docker 이미지를 Docker Hub에 배포하고, Linux VM(운영/스테이징 환경)에서 해당 이미지를 Pull 받아 실행하는 프로세스를 다룹니다.

1. 기본 명령어 (Common Commands)
프로젝트 구동 및 중지 시 사용하는 기본 명령어입니다.

컨테이너 빌드 및 백그라운드 실행

Bash
docker compose up -d --build
컨테이너 중지 및 제거

Bash
docker compose down
⚠️ 주의: docker compose down -v 명령어는 데이터 볼륨까지 영구 삭제합니다. DB 데이터나 업로드된 이미지 파일을 보존해야 한다면 절대 -v 옵션을 사용하지 마세요.

2. 이미지 배포 프로세스 (Windows 환경)
로컬에서 수정이 완료된 컨테이너를 이미지로 만들어 Docker Hub에 업로드합니다.

① 이미지 태그 설정 (Tagging)
Docker Hub에 푸시하기 위해 내계정ID/저장소명:태그 형식으로 이름을 변경합니다.

PowerShell
# docker tag [기존이미지명] [내계정ID]/[저장소명]:[태그]
docker tag oracle-backup-image  jeonghwan/oracle-xe:latest
docker tag spring-backup-image  jeonghwan/spring-api:latest
docker tag nuxt-backup-image    jeonghwan/nuxt-web:latest
② Docker Hub 업로드 (Push)
PowerShell
# 1. Docker Hub 로그인 (최초 1회)
docker login

# 2. 이미지 업로드
docker push jeonghwan/oracle-xe:latest
docker push jeonghwan/spring-api:latest
docker push jeonghwan/nuxt-web:latest
3. 마이그레이션 및 실행 (Linux VM 환경)
업로드된 이미지를 리눅스 환경으로 가져와서 배포를 완료합니다.

① 이미지 내려받기 (Pull)
Bash
docker pull jeonghwan/oracle-xe:latest
docker pull jeonghwan/spring-api:latest
docker pull jeonghwan/nuxt-web:latest
② 서비스 실행
docker-compose.yml 파일의 image 항목을 위에서 Pull 받은 이미지 명칭으로 수정 후 실행합니다.

Bash
docker compose up -d
