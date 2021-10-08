package com.example.cleanxyandroid.bottomNavFragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.cleanxyandroid.BookingActivity
import com.example.cleanxyandroid.R
import com.example.cleanxyandroid.ScheduleSlotActivity
import com.example.cleanxyandroid.progressServiceActivities.OtpEnterActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import java.util.*

@Suppress("DEPRECATED_IDENTITY_EQUALS", "DEPRECATION")
class HomeFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mapView : MapView

    private lateinit var mGoogleMap: GoogleMap

    private lateinit var lastLocation : Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object{
        private const val LOCATION_REQUEST_CODE = 1
        private const val AUTOCOMPLETE_REQUEST_CODE = 1
        private const val TAG : String = "ContentSlider"
    }

    private lateinit var mLayout : SlidingUpPanelLayout

    private lateinit var firstSelected : View
    private lateinit var secondSelected : View
    private lateinit var thirdSelected : View
    private lateinit var fourthSelected : View
    private lateinit var fifthSelected : View
    private lateinit var sixthSelected : View
    private lateinit var seventhSelected : View

    private lateinit var fields : List<Place.Field>

    private lateinit var db : FirebaseFirestore
    private lateinit var auth : FirebaseAuth

    private lateinit var progressDialog : ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        mapView = view.findViewById(R.id.map_view_home_fragment)

        mapView.getMapAsync(this)
        mapView.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        fields = listOf(Place.Field.ID, Place.Field.NAME)

        val locationSearchBar : View = view.findViewById(R.id.searchLocationBarHomeFragment)
        locationSearchBar.setOnClickListener {
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(requireContext())
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
        }

        db = FirebaseFirestore.getInstance()
        auth = Firebase.auth

        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setTitle("Checking for Booking...")
        progressDialog.setMessage("Please Wait")
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)

        val timerBtn : View = view.findViewById(R.id.timerIconHomeFragment)
        timerBtn.setOnClickListener {
            progressDialog.show()
            checkIfPhoneInOngoing()
        }

        mLayout = view.findViewById(R.id.slidingLayoutHomeFragment)

        mLayout.anchorPoint = 0.41f
        mLayout.setDragView(R.id.dragView)
        mLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED

        mLayout.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener{
            override fun onPanelSlide(panel: View?, slideOffset: Float) {
                Log.i(TAG, "onPanelSlide, offset $slideOffset")
            }

            override fun onPanelStateChanged(
                panel: View?,
                previousState: SlidingUpPanelLayout.PanelState?,
                newState: SlidingUpPanelLayout.PanelState?
            ) {
                Log.i(TAG, "onPanelStateChanged $newState")
            }

        })

        mLayout.setFadeOnClickListener {
            mLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        }

        firstSelected = view.findViewById(R.id.firstSelectedSlidePanelHomeFragment)
        secondSelected = view.findViewById(R.id.secondSelectedSlidePanelHomeFragment)
        thirdSelected = view.findViewById(R.id.thirdSelectedSlidePanelHomeFragment)
        fourthSelected = view.findViewById(R.id.fourthSelectedSlidePanelHomeFragment)
        fifthSelected = view.findViewById(R.id.fifthSelectedSlidePanelHomeFragment)
        sixthSelected = view.findViewById(R.id.sixthSelectedSlidePanelHomeFragment)
        seventhSelected = view.findViewById(R.id.seventhSelectedSlidePanelHomeFragment)

        firstSelected.visibility = View.GONE
        secondSelected.visibility = View.GONE
        thirdSelected.visibility = View.GONE
        fourthSelected.visibility = View.GONE
        fifthSelected.visibility = View.GONE
        sixthSelected.visibility = View.GONE
        seventhSelected.visibility = View.GONE

        var isFirstSelected = false
        var isSecondSelected = false
        var isThirdSelected = false
        var isFourthSelected = false
        var isFifthSelected  = false
        var isSixthSelected  = false
        var isSeventhSelected = false

        val firstService : View = view.findViewById(R.id.serviceFirstSlidePanelHomeFragment)
        val secondService : View = view.findViewById(R.id.serviceSecondSlidePanelHomeFragment)
        val thirdService : View = view.findViewById(R.id.serviceThirdSlidePanelHomeFragment)
        val fourthService : View = view.findViewById(R.id.serviceFourthSlidePanelHomeFragment)
        val fifthService : View = view.findViewById(R.id.serviceFifthSlidePanelHomeFragment)
        val sixthService : View = view.findViewById(R.id.serviceSixthSlidePanelHomeFragment)
        val seventhService : View = view.findViewById(R.id.serviceSeventhSlidePanelHomeFragment)

        firstService.setOnClickListener {
            if (isFirstSelected) {
                firstSelected.visibility = View.GONE
            }
            else {
                firstSelected.visibility = View.VISIBLE
            }
            isFirstSelected = !isFirstSelected
        }

        secondService.setOnClickListener {
            if (isSecondSelected) {
                secondSelected.visibility = View.GONE
            }
            else {
                secondSelected.visibility = View.VISIBLE
            }
            isSecondSelected = !isSecondSelected
        }

        thirdService.setOnClickListener {
            if (isThirdSelected) {
                thirdSelected.visibility = View.GONE
            }
            else {
                thirdSelected.visibility = View.VISIBLE
            }
            isThirdSelected = !isThirdSelected
        }

        fourthService.setOnClickListener {
            if (isFourthSelected) {
                fourthSelected.visibility = View.GONE
            }
            else {
                fourthSelected.visibility = View.VISIBLE
            }
            isFourthSelected = !isFourthSelected
        }

        fifthService.setOnClickListener {
            if (isFifthSelected) {
                fifthSelected.visibility = View.GONE
            }
            else {
                fifthSelected.visibility = View.VISIBLE
            }
            isFifthSelected = !isFifthSelected
        }

        sixthService.setOnClickListener {
            if (isSixthSelected) {
                sixthSelected.visibility = View.GONE
            }
            else {
                sixthSelected.visibility = View.VISIBLE
            }
            isSixthSelected = !isSixthSelected
        }

        seventhService.setOnClickListener {
            if (isSeventhSelected) {
                seventhSelected.visibility = View.GONE
            }
            else {
                seventhSelected.visibility = View.VISIBLE
            }
            isSeventhSelected = !isSeventhSelected
        }


        val bookBtn : Button = view.findViewById(R.id.bookBtnSlidePanelHomeFragment)
        val scheduleBtn : Button = view.findViewById(R.id.scheduleBtnSlidePanelHomeFragment)

        bookBtn.setOnClickListener {
            when (noOfServicesSelected(isFirstSelected, isSecondSelected, isThirdSelected, isFourthSelected, isFifthSelected, isSixthSelected, isSeventhSelected)) {
                0 -> {
                    Toast.makeText(requireActivity(), "Please select a service to schedule", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val selectedServices = arrayOf(0,0,0,0,0,0,0)
                    if (isFirstSelected)selectedServices[0]=1
                    if (isSecondSelected)selectedServices[1]=1
                    if (isThirdSelected)selectedServices[2]=1
                    if (isFourthSelected)selectedServices[3]=1
                    if (isFifthSelected)selectedServices[4]=1
                    if (isSixthSelected)selectedServices[5]=1
                    if (isSeventhSelected)selectedServices[6]=1

                    val c = Calendar.getInstance()

                    val year = c.get(Calendar.YEAR)
                    val month = c.get(Calendar.MONTH)
                    val day = c.get(Calendar.DAY_OF_MONTH)

                    var hour = c.get(Calendar.HOUR_OF_DAY)
                    val minute = c.get(Calendar.MINUTE)

                    val amOrPm: String

                    if (hour <= 7) {
                        Toast.makeText(requireContext(), "Services are only available from 7 Am to 8 Pm", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        if (hour <= 12) {
                            amOrPm = "am"
                        }
                        else {
                            hour -= 12
                            amOrPm = "pm"
                        }

                        val fullTime = arrayOf(0,0,0,0,0,0)
                        fullTime[0] = hour
                        fullTime[1] = minute
                        if (amOrPm == "am") {
                            fullTime[2] = 1
                        }
                        else {
                            fullTime[2] = 2
                        }
                        fullTime[3] = day
                        fullTime[4] = month + 1
                        fullTime[5] = year

                        val intent = Intent(requireContext(), BookingActivity::class.java)
                        intent.putExtra("ss", selectedServices)
                        intent.putExtra("fullTime", fullTime)
                        startActivity(intent)
                    }

                }
            }
        }

        scheduleBtn.setOnClickListener {
            when (noOfServicesSelected(isFirstSelected, isSecondSelected, isThirdSelected, isFourthSelected, isFifthSelected, isSixthSelected, isSeventhSelected)) {
                0 -> {
                    Toast.makeText(requireActivity(), "Please select a service to schedule", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val selectedServices = arrayOf(0,0,0,0,0,0,0)
                    if (isFirstSelected)selectedServices[0]=1
                    if (isSecondSelected)selectedServices[1]=1
                    if (isThirdSelected)selectedServices[2]=1
                    if (isFourthSelected)selectedServices[3]=1
                    if (isFifthSelected)selectedServices[4]=1
                    if (isSixthSelected)selectedServices[5]=1
                    if (isSeventhSelected)selectedServices[6]=1

                    val intent = Intent(requireContext(), ScheduleSlotActivity::class.java)
                    intent.putExtra("ss", selectedServices)
                    startActivity(intent)
                }
            }
        }

        return view

    }

    private fun checkIfPhoneInOngoing() {
        val currentUser = auth.currentUser
        db.collection("Ongoing").document(currentUser?.phoneNumber.toString()).get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val doc = it.result
                    if (doc.exists()) {
                        checkIfServiceIsOngoing()
                    }
                    else {
                        progressDialog.dismiss()
                        Toast.makeText(requireContext(), "No Ongoing Booking", Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    progressDialog.dismiss()
                    Toast.makeText(requireContext(), "No Ongoing Booking", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun checkIfServiceIsOngoing() {
        val currentUser = auth.currentUser
        db.collection("Ongoing").document(currentUser?.phoneNumber.toString()).get()
            .addOnSuccessListener {
                val x = it.get("ongoing") as Long?
                if (x == 1L) {
                    progressDialog.dismiss()
                    startActivity(Intent(requireContext(), OtpEnterActivity::class.java))
                }
                else {
                    progressDialog.dismiss()
                    Toast.makeText(requireContext(), "No Ongoing Booking", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(requireContext(), "Please try again later", Toast.LENGTH_SHORT).show()
            }
    }

    private fun noOfServicesSelected(
        firstSelected: Boolean,
        secondSelected: Boolean,
        thirdSelected: Boolean,
        fourthSelected: Boolean,
        fifthSelected: Boolean,
        sixthSelected: Boolean,
        seventhSelected: Boolean
    ): Int {
        var c = 0
        if (firstSelected)c++
        if (secondSelected)c++
        if (thirdSelected)c++
        if (fourthSelected)c++
        if (fifthSelected)c++
        if (sixthSelected)c++
        if (seventhSelected)c++

        return c
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), getString(R.string.places_api_key), Locale.US)
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
        mGoogleMap.setOnMarkerClickListener(this)

        setUpMap()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        Log.i("Autocomplete data", "Place: ${place.name}, ${place.id}")

                        val bundle = data.extras

                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        Log.i("Autocomplete data", status.statusMessage)
                    }
                }
                Activity.RESULT_CANCELED -> {
                    Log.i("Autocomplete data", "The user canceled the operation")
                }
            }
        }
    }

    @SuppressLint("ResourceType")
    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE)

            return
        }
        mGoogleMap.isMyLocationEnabled = true


//        setMapViewToIndia()

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->

            if (location != null ) {
                lastLocation = location
                val currentLatLong = LatLng(location.latitude, location.longitude)
                placeMarkerOnMap(currentLatLong)
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 12f))
            }

        }
    }

    private fun setMapViewToIndia() {
        val indiaLatLng = LatLngBounds(LatLng(23.6936,68.14712), LatLng(28.20453,97.34466))
        mGoogleMap.setLatLngBoundsForCameraTarget(indiaLatLng)
    }

    private fun placeMarkerOnMap(currentLatLong: LatLng) {
        val markerOptions = MarkerOptions().position(currentLatLong)
        markerOptions.title("$currentLatLong")
        mGoogleMap.addMarker(markerOptions)

    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()

    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onMarkerClick(p0: Marker): Boolean = false

}