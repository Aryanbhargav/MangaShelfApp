# MangaShelf

## App Previews

<img width="327" alt="Screenshot 2025-02-25 at 12 52 13 AM" src="https://github.com/user-attachments/assets/1303baf3-fb43-4094-80d0-0babdbc89c5f" />
<img width="321" alt="Screenshot 2025-02-25 at 12 53 03 AM" src="https://github.com/user-attachments/assets/7e6be8ea-dd78-4a33-a353-4ff6bf7891dd" />
<img width="322" alt="Screenshot 2025-02-25 at 12 53 16 AM" src="https://github.com/user-attachments/assets/bd5847b3-30b2-4b3c-a448-3238f1081c0a" />
<img width="324" alt="Screenshot 2025-02-25 at 12 52 48 AM" src="https://github.com/user-attachments/assets/2ab93179-a9ce-400d-b179-810b0f9a02c7" />

## Setup Process

1. **Download the ZIP**  
   - Go to the [GitHub repository](<repository_link_here>).  
   - Click on the **"Code"** button and select **"Download ZIP"**.  
   - Extract the downloaded ZIP file to your desired location.  

2. **Import into Android Studio**  
   - Open **Android Studio**.  
   - Click on **"Open"**.  
   - Navigate to the extracted folder and select it.  
   - Wait for the project to sync and build.  

3. **Run the App**  
   - Connect an emulator or physical device.
   - Run the project from 'main' branch
   - Click on the **Run** button ▶️ to launch the app.  

## Project Architecture

MangaShelf follows **Clean Architecture** with the **MVVM** design pattern. The project is structured into three main layers:  

1. **Data Layer**  
   - Handles data operations from **Network (Retrofit)** and **Local Database (Room)**.  

2. **Domain Layer**  
   - Contains repository implementations to abstract data sources.  

3. **UI Layer**  
   - Built with **Jetpack Compose** and consists of **Composables** and **ViewModel** to manage UI state.  

### Tech Stack

- **Networking** → Retrofit  
- **Local Storage** → Room  
- **Dependency Injection** → Hilt  
- **Async Operations** → Kotlin Coroutines & Flow  
- **UI Framework** → Jetpack Compose  
- **Image Loading** → Coil  
- **Lifecycle Management** → ViewModel 

