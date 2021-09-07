package com.example.cleanxyandroid.bottomNavFragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.DisplayMetrics
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

        var isFirstSelected : Boolean = false
        var isSecondSelected : Boolean = false
        var isThirdSelected : Boolean = false
        var isFourthSelected : Boolean = false
        var isFifthSelected : Boolean = false
        var isSixthSelected : Boolean = false
        var isSeventhSelected : Boolean = false

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
            if (checkIfServiceIsSelected(isFirstSelected, isSecondSelected, isThirdSelected, isFourthSelected, isFifthSelected, isSixthSelected, isSeventhSelected)) {
                val selectedServices = arrayOf(0,0,0,0,0,0,0)
                if (isFirstSelected)selectedServices[0]=1
                if (isSecondSelected)selectedServices[1]=1
                if (isThirdSelected)selectedServices[2]=1
                if (isFourthSelected)selectedServices[3]=1
                if (isFifthSelected)selectedServices[4]=1
                if (isSixthSelected)selectedServices[5]=1
                if (isSeventhSelected)selectedServices[6]=1
            }
            else {
                Toast.makeText(requireActivity(), "Please select a service to book", Toast.LENGTH_SHORT).show()
            }
        }

        scheduleBtn.setOnClickListener {
            if (checkIfServiceIsSelected(isFirstSelected, isSecondSelected, isThirdSelected, isFourthSelected, isFifthSelected, isSixthSelected, isSeventhSelected)) {
                val selectedServices = arrayOf(0,0,0,0,0,0,0)
                if (isFirstSelected)selectedServices[0]=1
                if (isSecondSelected)selectedServices[1]=1
                if (isThirdSelected)selectedServices[2]=1
                if (isFourthSelected)selectedServices[3]=1
                if (isFifthSelected)selectedServices[4]=1
                if (isSixthSelected)selectedServices[5]=1
                if (isSeventhSelected)selectedServices[6]=1

                val intent = Intent(requireContext(), ScheduleSlotActivity::class.java)
                intent.putExtra("key", selectedServices)
                startActivity(intent)

                startActivity(Intent(requireContext(), ScheduleSlotActivity::class.java))
            }
            else {
                Toast.makeText(requireActivity(), "Please select a service to schedule", Toast.LENGTH_SHORT).show()
            }
        }

        return view
        
    }

    private fun checkIfServiceIsSelected(first: Boolean, second: Boolean, third: Boolean, fourth: Boolean, fifth: Boolean, sixth: Boolean, seventh: Boolean): Boolean {
        return first || second || third || fourth || fifth || sixth || seventh
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), getString(R.string.places_api_key), Locale.US)
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap

//        mGoogleMap.uiSettings.isZoomControlsEnabled = true
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