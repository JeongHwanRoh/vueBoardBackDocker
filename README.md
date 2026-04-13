# 🚀 Docker Hub 기반 프로젝트 마이그레이션 가이드

본 문서는 **Windows(개발 환경)**에서 수정한 Docker 이미지를 **Docker Hub**에 배포하고, **Linux VM(운영/스테이징 환경)**에서 해당 이미지를 Pull 받아 실행하는 전체 프로세스를 정리합니다.

---

## 1. Docker 기본 명령어 (Common Commands)

컨테이너 관리 및 리스크 방지를 위한 기본 명령어입니다.

* **컨테이너 빌드 및 백그라운드 실행**
    ```bash
    docker compose up -d --build
    ```
* **컨테이너 중지 및 제거 (데이터 보존)**
    ```bash
    docker compose down
    ```
* **⚠️ 주의: 데이터 휘발 방지**
    - `docker compose down -v` 명령어 사용 시 **데이터 볼륨 자체가 삭제**됩니다.
    - DB 데이터, 업로드 파일 등을 날리고 싶지 않다면 최초 구성 이후 `-v` 옵션은 절대 사용하지 마세요.

---

## 2. 이미지 배포 프로세스 (From Windows)

로컬에서 SQL 수정 및 최적화가 완료된 컨테이너를 이미지화하여 업로드합니다.

### ① 이미지 태그 설정 (Tagging)
Docker Hub 저장소 규칙(`내계정ID/저장소명:태그`)에 맞춰 이미지 이름을 변경합니다.
```powershell
① docker tag [기존이미지명] [내계정ID]/[저장소명]:[태그]
docker tag oracle-backup-image  jeonghwan/oracle-xe:latest
docker tag spring-backup-image  jeonghwan/spring-api:latest
docker tag nuxt-backup-image    jeonghwan/nuxt-web:latest

## 3. 도커 허브 업로드(push)
①Docker Hub 로그인 (최초 1회 실행)
docker login

② 이미지 업로드
docker push jeonghwan/oracle-xe:latest
docker push jeonghwan/spring-api:latest
docker push jeonghwan/nuxt-web:latest

## 4. 마이그레이션 및 실행
① 이미지 내려받기 (Pull)
Bash
docker pull jeonghwan/oracle-xe:latest
docker pull jeonghwan/spring-api:latest
docker pull jeonghwan/nuxt-web:latest
② 서비스 실행
docker-compose.yml 파일 내 image 항목을 위에서 Pull 받은 명칭으로 수정한 뒤 실행합니다.

Bash
# 실행 전 docker-compose.yml 파일 내 image 경로 수정 필수
docker compose up -d
