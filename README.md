# com.cdsoftware.docuware

- Copyright: 2026 https://www.casadelsoftware.com
- Repository: https://github.com/lcdsoftware/com.cdsoftware.docuware
- License: GPL 2

## Description

An iDempiere 12 OSGi storage-provider integration under development for DocuWare. The current implementation registers attachment and archive storage services. Archive loading can authenticate with DocuWare and download a referenced PDF; the remaining attachment and archive operations are scaffolds and are not operational yet.

## Contributors

- 2025 Javier Galindo <javiergalindo@casadelsoftware.com>.

## Components

- iDempiere Plugin [com.cdsoftware.docuware](com.cdsoftware.docuware)
- iDempiere Unit Test Fragment [com.cdsoftware.docuware.test](com.cdsoftware.docuware.test)

## Prerequisites

- Java 17, commands `java` and `javac`.
- iDempiere 12.
- An accessible DocuWare service and credentials authorized to log in and download documents.
- An iDempiere Storage Provider record configured with the DocuWare method, service URL, username, password and file cabinet ID in the folder field.

## Features/Documentation

### Source Structure

```text
com.cdsoftware.docuware/src
└── com
    └── cdsoftware
        └── docuware
            ├── base
            │   ├── BundleInfo.java
            │   ├── CustomCallout.java
            │   ├── CustomEvent.java
            │   ├── CustomForm.java
            │   └── CustomProcess.java
            ├── component
            │   ├── CalloutFactory.java
            │   ├── EventFactory.java
            │   ├── FormFactory.java
            │   ├── ModelFactory.java
            │   └── ProcessFactory.java
            ├── model
            │   ├── ArchiveDocuware.java
            │   └── AttachmentDocuware.java
            └── util
                ├── DMSConfig.java
                ├── DocuwareUtil.java
                ├── KeyValueLogger.java
                ├── SqlBuilder.java
                └── TimestampUtil.java
com.cdsoftware.docuware.test/src
└── com
    └── cdsoftware
        └── docuware
            ├── test
            │   ├── assertion
            │   │   └── Annotations.java
            │   └── util
            │       ├── RandomTestUtil.java
            │       └── ReflectionTestUtil.java
            └── util
                ├── FileTemplateBuilderTest.java
                ├── KeyValueLoggerTest.java
                ├── SqlBuilderTest.java
                └── TimestampUtilTest.java
```

### Storage Services

| Service | iDempiere interface | Current behavior |
| --- | --- | --- |
| `ArchiveDocuware` | `IArchiveStore` | `loadLOBData` obtains the Storage Provider configuration, logs in through `Account/Logon`, reads the DocuWare document reference from the archive binary data and downloads the PDF from `FileCabinets/{fileCabinetId}/Documents/{documentId}/FileDownload`. If login or download fails, it returns the locally stored archive bytes. Save, delete and flush behavior are not implemented. |
| `AttachmentDocuware` | `IAttachmentStore` | Registered as a DocuWare attachment store, but load, save, whole-attachment delete and entry delete currently return `false` without performing an operation. |

The services are registered through OSGi Declarative Services. `ArchiveDocuware` uses storage method `Docuware` with ranking `100`; `AttachmentDocuware` uses the same method with ranking `10`. Annotation-based factories are also registered, but the source tree contains no concrete process, callout, event or form implementations.

### DocuWare Connection

`DocuwareUtil` builds its configuration from the iDempiere Storage Provider:

- URL: DocuWare platform base URL. Configure it with a trailing `/` because endpoints are appended directly.
- Username and password: submitted to `Account/Logon` as form data.
- Folder: interpreted as the DocuWare file cabinet ID.
- Archive binary data: interpreted as the DocuWare document ID for archive loading.

A successful login collects the DocuWare authentication cookies returned by the server and sends them with the subsequent document download request.

### Implementation Status

- Archive document loading is the only implemented storage operation.
- Archive save, delete and flush are placeholders.
- All attachment storage operations are placeholders.
- `DocuwareUtil` contains additional placeholder methods for upload, check-in, check-out and document lookup.
- The plugin includes no Application Dictionary 2Pack ZIP or setup process. `OSGI-INF/*.xml` files are OSGi service descriptors, and the test fragment's `xml-invoice.xml` is a test resource.

## Instructions

1. Configure a DocuWare platform endpoint and credentials that can authenticate and download documents from the target file cabinet.
2. Install the `com.cdsoftware.docuware` OSGi bundle in iDempiere 12 and refresh or restart the runtime.
3. Create an iDempiere Storage Provider using method `Docuware`. Enter the platform base URL with a trailing `/`, the username and password, and the file cabinet ID in the folder field.
4. Use the provider for archive loading only after validating it in a controlled environment. Do not enable it for attachment persistence or archive save/delete operations until those methods are implemented and tested.

## Extra Links

- [DocuWare Developer Documentation](https://developer.docuware.com/)
- [iDempiere](https://www.idempiere.org/)
