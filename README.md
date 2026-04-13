# 🚀 Docker Hub 기반 프로젝트 마이그레이션 가이드

본 문서는 **Windows(개발 환경)**에서 수정한 Docker 이미지를 **Docker Hub**에 배포하고, **Linux VM(운영/스테이징 환경)**에서 해당 이미지를 Pull 받아 실행하는 전체 프로세스를 정리합니다.

---

## 📦 1. Docker 기본 명령어 (Common Commands)

컨테이너 관리 및 데이터 보호를 위한 핵심 명령어입니다.

### ▶️ 컨테이너 빌드 및 실행

```bash
docker compose up -d --build
```

### ⛔ 컨테이너 중지 및 제거 (데이터 유지)

```bash
docker compose down
```

### ⚠️ 데이터 휘발 주의

```bash
docker compose down -v
```

* 위 명령어는 **볼륨(Volume)까지 삭제**
* DB 데이터 및 업로드 파일이 모두 사라짐
  👉 **운영 환경에서는 절대 사용 금지**

---

## 🏗️ 2. 이미지 배포 프로세스 (From Windows)

로컬에서 수정된 컨테이너를 Docker 이미지로 만들어 Docker Hub에 업로드합니다.

### ① 이미지 태그 설정 (Tagging)

Docker Hub 규칙:
`<DockerHubID>/<Repository>:<Tag>`

```powershell
docker tag oracle-backup-image  jeonghwan/oracle-xe:latest
docker tag spring-backup-image  jeonghwan/spring-api:latest
docker tag nuxt-backup-image    jeonghwan/nuxt-web:latest
```

---

### ② Docker Hub 업로드 (Push)

```powershell
# 1. 로그인 (최초 1회)
docker login

# 2. 이미지 업로드
docker push jeonghwan/oracle-xe:latest
docker push jeonghwan/spring-api:latest
docker push jeonghwan/nuxt-web:latest
```

---

## 🐧 3. 마이그레이션 및 실행 (From Linux VM)

Linux VM 환경에서 이미지를 Pull 받아 서비스 실행합니다.

### ① 이미지 다운로드 (Pull)

```bash
docker pull jeonghwan/oracle-xe:latest
docker pull jeonghwan/spring-api:latest
docker pull jeonghwan/nuxt-web:latest
```

---

### ② 서비스 실행

1. `docker-compose.yml` 파일에서 image 경로 수정
2. 컨테이너 실행

```bash
docker compose up -d
```

---

