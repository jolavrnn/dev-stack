## Description

Setup of custom Keycloak theme.

## Overview

- `test` &larr; Custom Keycloak Theme for login using username/password.
- `index.html` &larr; Standalone page to preview the theme outside Keycloak

## Usage

### 1. Preview the theme
Open: [index.html](index.html) in Browser.

### 2. Start Keycloak with the custom theme

```sh
docker compose up -d
```
### 3. Access Keycloak

Open in browser: http://localhost:8080
Default:
- Username: `admin`
- Password: `my_superSecureP4ssword`

### 4. Uninstall
```sh
docker compose down --volumes
```

## Reference

- [Keycloak Theme guideliness](https://www.keycloak.org/ui-customization/themes)
- [Theme](https://freefrontend.com/css-login-forms/)
- [Keycloak Theme structure](https://github.com/keycloak/keycloak/tree/main/themes/src/main/resources/theme)
