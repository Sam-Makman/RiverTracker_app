# River Tracker App

<h2>Features </h2>
<ul>
<li> Sign up for new account </li>
<li> Login to account</li>
<li> View favorite rivers</li>
<li> View and search all rivers</li>
<li> View river details and alerts</li>
<li> search rivers by map/location</li>
</ul>

<h2> Resources </h2>
<ul>
<li> Our own API  </li>
<li>  USGS streamflows API</li>
<li>Google Maps API  </li>
<li> Butterknife </li>
<li> Piccaso </li>
</ul>

<h4> Sign up</h4>
This is the default activity. You can sign up for a new account, login to an existing account or continue without an account.
When you create a new account the information is sent to our backend and a new api token is issued to the user. If the user already
has an api token then they are automatically moved from this activity to the favorites activity.


<h4>Login </h4>
This activity allows the user to login. It send ths user information to our api and then gets a api token back. after loggin in the
user is sent to the favorites activity

<h4> favorites activity</h4>
The favorites activity loads all of the users favorite rivers into a recycler view to be desplayed. This activity display all
basic river information including name, section, difficulty, discharge, state and if there are any alerts. Each river can be clicked 
on to take you to a detail activity

<h4> Search </h4>
You can search for rivers by name, section or difficulty. State will be added to this list at some point. 
The search query is sent to our api. The results are displayed in a RiversFragment (same fragment used by favorites).

<h4> Details </h4>
the River detail activity shows all of the same details that are in the cardview plus the river description and it will give 
the details for all alerts on the river. 

<h2> To Do </h2>
<ul>
<li> Add ability to create account </li>
<li> add search by map
  <ul><li> add map to details activity </li> 
  <li> have map activity  </li>
  <li> send put in or take out coordinates to google maps app  </li>
  </ul>
</li>
<li> Add searching by more fields</li>
<li> Allow Sorting  </li>
<li> Creation of alerts on app (If time allows) </li>
<li> Being able to mark alerts as old (if time allows) </li>
</ul>
