# com.cdsoftware.docuware
- Copyright: 2026 https://www.casadelsoftware.com
- Repository: https://bitbucket.org/cdsoftware/com.cdsoftware.docuware.git
- License: GPL 2

## Description
The `com.cdsoftware.docuware` plugin is a custom extension for iDempiere. It extends standard system capabilities by providing database models, and Application Dictionary configurations (2Pack) to support customized business workflows.

## Contributors
- 2026 Casa del Software <info@casadelsoftware.com>

## Components
- iDempiere Plugin [com.cdsoftware.docuware](com.cdsoftware.docuware)
- iDempiere Unit Test Fragment [com.cdsoftware.docuware.test](com.cdsoftware.docuware.test)

## Prerequisites
- Java 11, commands `java` and `javac`.
- iDempiere 11

## Features/Documentation
### Source Structure
```
├── com/
        ├── cdsoftware/
            ├── docuware/
                ├── util/
                    ├── DMSConfig.java
                    ├── DocuwareUtil.java
                    ├── KeyValueLogger.java
                    ├── SqlBuilder.java
                    ├── TimestampUtil.java
                ├── model/
                    ├── ArchiveDocuware.java
                    ├── AttachmentDocuware.java
                ├── base/
                    ├── BundleInfo.java
                    ├── CustomCallout.java
                    ├── CustomEvent.java
                    ├── CustomForm.java
                    ├── CustomProcess.java
                ├── component/
                    ├── CalloutFactory.java
                    ├── EventFactory.java
                    ├── FormFactory.java
                    ├── ModelFactory.java
                    ├── ProcessFactory.java
```




### Generated Models

| Model | Table | Functional role |
| --- | --- | --- |
| `ArchiveDocuware` | `ArchiveDocuware` | Represents database records and implements custom business logic. |
| `AttachmentDocuware` | `AttachmentDocuware` | Represents database records and implements custom business logic. |


### Application Dictionary Metadata (2Pack)

| Package / File Name | Purpose & Dictionary Configurations |
| --- | --- |
| `CalloutFactory.xml` | Metadata package containing Application Dictionary (AD) configurations. |
| `EventFactory.xml` | Metadata package containing Application Dictionary (AD) configurations. |
| `FormFactory.xml` | Metadata package containing Application Dictionary (AD) configurations. |
| `ModelFactory.xml` | Metadata package containing Application Dictionary (AD) configurations. |
| `ProcessFactory.xml` | Metadata package containing Application Dictionary (AD) configurations. |
| `archivedocuware.xml` | Metadata package containing Application Dictionary (AD) configurations. |
| `attachmentdocuware.xml` | Metadata package containing Application Dictionary (AD) configurations. |
| `xml-invoice.xml` | Metadata package containing Application Dictionary (AD) configurations. |


## Instructions
1. Deploy the `com.cdsoftware.docuware` OSGi bundle in your iDempiere environment.
2. Restart iDempiere and refresh OSGi bundles to register factories.
3. Configure dictionary and role access rules as needed.
