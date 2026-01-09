# MindVault – Personal Knowledge Management System 

MindVault is a **web-based application demo**, supported by a **basic Kotlin Android app**, that demonstrates how users can store information and interact with an AI-powered assistant through a simple interface.

---

## What we built

We built:

- A **demo website** that allows users to store data and query it using an AI assistant
- A **basic Android application (Kotlin)** to understand mobile UI flow and client-side interaction
- A simple, clean interface focused on usability and clarity

The website was exposed temporarily using **ngrok** for development and demonstration purposes.

---

## Demo interface

The website provides two main actions:

- **Store Data**  
  Upload or submit information that can later be referenced.

- **Query & Retrieve**  
  Ask questions and receive AI-generated responses based on previously stored data.

A sample UI screenshot is shown below:

<img width="1280" height="686" alt="AI Chat Assistants UI" src="https://github.com/user-attachments/assets/28732caf-cebb-4b2d-8750-b5e9a0e4c782" />
<img width="240" height="580" alt="image" src="https://github.com/user-attachments/assets/27c6e2b6-7e4a-4d6c-b7f3-67df46333a6f" />
<img width="240" height="580" alt="image" src="https://github.com/user-attachments/assets/cd8b2eb2-70aa-4c8a-9714-ce578957f591" />
<img width="240" height="580" alt="image" src="https://github.com/user-attachments/assets/663f8e03-4307-4c1a-95ea-b65403a358cf" />

---

## Android app (basic overview)

Along with the web demo, a **basic Android application** was built using:

- **Kotlin**
- Simple activity-based navigation
- XML layouts for UI
- Basic screens for interaction and testing

The Android app was created mainly to:
- Learn Android app structure
- Understand client-side interaction
- Explore how a mobile interface can connect to an AI-powered system

> The Android app code shared here does not expose any sensitive logic or service configuration.

---

## How the demo works (high level)

- The frontend provides a simple interface for user interaction.
- User inputs are sent to an AI service to generate responses.
- **ngrok** was used to expose the local development server during testing.

> ⚠️ Internal logic, AI workflows, backend services, and configurations are **not included or described** in this repository.

---

## Running the demo (local)

This repository focuses on **demonstration and UI behavior**.

To run a similar demo locally:
1. Set up a local web server of your choice.
2. Connect it to an AI service endpoint (your own).
3. Optionally expose it using a tunneling tool such as ngrok for testing.

No production or deployment instructions are included.

---

## Security & privacy

- This repository contains **no secrets, keys, or credentials**.
- Any sensitive configuration should remain local and never be committed.
- Do not upload real or personal data when testing demos.

---

## Where to get help

- General web development documentation
- Android developer documentation
- ngrok documentation: https://ngrok.com/docs
- AI tooling documentation (provider of your choice)
- GitHub Issues for questions related to this repository

---

## Contributing

- Contributions are welcome for UI improvements or documentation clarity.
- Please keep all changes **sanitized**.
- Do not add internal logic, credentials, or service configurations.

---

Thank you for checking out MindVault.

This project is shared to demonstrate **concepts, UI behavior, and learning outcomes only**.
