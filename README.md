# 🚀 Docker Hub 기반 프로젝트 마이그레이션 가이드

본 문서는 **Windows(개발 환경)**에서 수정한 Docker 이미지를 **Docker Hub**에 배포하고, **Linux VM(운영/스테이징 환경)**에서 해당 이미지를 Pull 받아 실행하는 전체 프로세스를 정리합니다.

---

## 1. Docker 기본 명령어 (Common Commands)

컨테이너 관리 및 리스크 방지를 위한 기본 명령어입니다.

* **컨테이너 빌드 및 백그라운드 실행**
  ```bash
  docker compose up -d --build
컨테이너 중지 및 제거 (데이터 보존)

Bash
docker compose down
⚠️ 주의: 데이터 휘발 방지

docker compose down -v 명령어 사용 시 데이터 볼륨 자체가 삭제됩니다.

DB 데이터, 업로드 파일 등을 보존해야 한다면 최초 구성 이후 -v 옵션은 절대 사용하지 마세요.

2. 이미지 태그 설정 (Tagging)
Docker Hub 저장소 규칙(내계정ID/저장소명:태그)에 맞춰 로컬 이미지 이름을 변경합니다.

PowerShell
# 사용법: docker tag [기존이미지명] [내계정ID]/[저장소명]:[태그]
docker tag oracle-backup-image  jeonghwan/oracle-xe:latest
docker tag spring-backup-image  jeonghwan/spring-api:latest
docker tag nuxt-backup-image    jeonghwan/nuxt-web:latest
3. 도커 허브 업로드 (Push)
수정 및 최적화가 완료된 이미지를 원격 저장소에 업로드합니다.

Docker Hub 로그인 (최초 1회 실행)

PowerShell
docker login
이미지 업로드

PowerShell
docker push jeonghwan/oracle-xe:latest
docker push jeonghwan/spring-api:latest
docker push jeonghwan/nuxt-web:latest
4. 마이그레이션 및 실행 (Linux VM)
이전 대상인 리눅스 환경에서 이미지를 내려받고 서비스를 기동합니다.

이미지 내려받기 (Pull)

Bash
docker pull jeonghwan/oracle-xe:latest
docker pull jeonghwan/spring-api:latest
docker pull jeonghwan/nuxt-web:latest
서비스 실행

💡 중요: 실행 전 docker-compose.yml 파일 내 image 항목을 위에서 Pull 받은 명칭으로 수정해야 합니다.

Bash
# 컨테이너 실행
docker compose up -d
🛠️ 핵심 체크리스트 (Critical Checklist)
DB 데이터 연속성: docker push는 볼륨 데이터를 포함하지 않습니다. 수정된 sql.init 파일은 직접 리눅스의 ./oracle/init 경로에 복사하여 초기 기동 시 DB가 자동 구성되도록 하세요.

네트워크 설정: 리눅스 VM IP가 변경되었다면, Spring Boot의 CORS 허용 도메인과 Nuxt3의 API Endpoint 주소를 해당 IP에 맞게 업데이트해야 합니다.

HTTPS 적용: 리눅스에서 mkcert를 통해 인증서를 발급받고, Nginx 볼륨 매핑을 완료해야 정상적인 SSL 통신이 가능합니다.
