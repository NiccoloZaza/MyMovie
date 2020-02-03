# MyMovie
MyMovie is application for testing purposes. Every 3rd party library or custom made component used in this project is purely for personal usage.

# Used 3rd party Components
- Glide - Used to cache images in order to display them in offline mode and improve overall performance and quality of image processing/caching.
- Realm - Used to cache Favorite Movies in local database.
- Retrofit - Used to connect to API.

# Custom Components
- NetworkLib - Generalised code to detect internet connection changes.
- FancySelector - Custom Generic and Dynamic View used to list selectable items and fulfil the purpose of movie filter.
- ServiceContainer - Service caching Singleton class which is very similar to ASP.NET Containers.
- ServiceExecutioner - Used to make service calls and handling responses easier.

# Modules
- app - Pure Android module which contains code only connected to android.
- glib - Compilation of generalised components including API connections and cache control.
- networklib - Generalised module which helps out with connectivity handling.

# Used Patterns
- Dependency Injection - Used in ServiceContainer to store all services in one Singleton class.
- Bridge - Used in glib which provides only interfaces to app module.
- Builder - Used in ServiceExecutioner.
- Singleton - Used in ServiceContainer.
- Observer - Used in NetworkLib.

# Important 
Almost everything in this project(in it's current state) is generalised and moved to it's "abstract" state in glib/networklib module to make
future development easier and less time-consuming. Examples:
- BaseCompatActivity/BaseFragment
  Provides a bridge to Network handling.
- BaseRecyclerAdapter/BaseItemViewHolder
  Provides tools to manage RecyclerView easier and faster.
- Whole NetworkLib idea gives good set of tools to completely avoid unnecessary code in base project and improve laconic approach to it.

# Notable Application Structure decisions
- Movies list pages
  In order to make user experience with filtering movies somewhat easier, I decided to put BottomNavigationView for filtering Favorite movies
and FancySelector to filter Top Rated and Popular movies
- Handling Connectivity changes
  In order to make user experience with connectivity changes more enjoyable, I decided to autimatically update or retreive new data. For example, if user opens application with connection, downloads data and then scrolls all the way to bottom(In order to get to next page) but turns of connection before doing so, Application will automatically update data/get next page when connection comes back

# Api Key Instructions
  In order to apply your api key to application, please head over to glib module, ApplicationInstance class and put your key in field provided in image below:
  - ![GitHub Logo](https://i.ibb.co/kMfmtrL/Capture.jpg)

# Thanks
- developers.themoviedb.org for providing API

# Author
Nika Sukiashvili - @NiccoloZaza on GitHub

# Disclaimer
This is not an official product.
