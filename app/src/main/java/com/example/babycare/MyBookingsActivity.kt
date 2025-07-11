//package com.example.babycare
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.RecyclerView
//
//
//class MyBookingsActivity : AppCompatActivity() {
//
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var bookingList: MutableList<Booking>
//    private lateinit var adapter: BookingAdapter
//    private val databaseRef = FirebaseDatabase.getInstance().getReference("bookings")
//    private val providerUid = FirebaseAuth.getInstance().currentUser?.uid ?: ""
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_my_bookings)
//
//        recyclerView = findViewById(R.id.recyclerViewBookings)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        bookingList = mutableListOf()
//        adapter = BookingAdapter(bookingList) { booking, action ->
//            handleBookingAction(booking, action)
//        }
//        recyclerView.adapter = adapter
//
//        loadBookings()
//    }
//
//    private fun loadBookings() {
//        databaseRef.orderByChild("providerId").equalTo(providerUid)
//            .addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    bookingList.clear()
//                    for (child in snapshot.children) {
//                        val booking = child.getValue(Booking::class.java)
//                        if (booking != null) {
//                            bookingList.add(booking)
//                        }
//                    }
//                    adapter.notifyDataSetChanged()
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    Toast.makeText(this@MyBookingsActivity, "Error loading bookings", Toast.LENGTH_SHORT).show()
//                }
//            })
//    }
//
//    private fun handleBookingAction(booking: Booking, action: String) {
//        val bookingRef = databaseRef.child(booking.bookingId)
//        bookingRef.child("status").setValue(action)
//            .addOnSuccessListener {
//                Toast.makeText(this, "Booking $action", Toast.LENGTH_SHORT).show()
//            }
//            .addOnFailureListener {
//                Toast.makeText(this, "Failed to update booking", Toast.LENGTH_SHORT).show()
//            }
//    }
//}
