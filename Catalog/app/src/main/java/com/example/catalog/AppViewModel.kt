package com.example.catalog

import android.app.Application
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.ItemTouchHelper


class AppViewModel : ViewModel() {

    var categories: Array<String> = arrayOf()

    private val _selectedCat: MutableLiveData<String> = MutableLiveData(EMPTYSTRING)
    var selectedCat: LiveData<String> = _selectedCat

    private val _bandList: MutableLiveData<MutableList<ListElement>> = MutableLiveData(bands)
    var bandList: LiveData<MutableList<ListElement>> = _bandList


    fun getFilteredBands(): MutableList<ListElement> {
        return when {
            (selectedCat.value == NOFILTER) -> bandList.value!!
            (selectedCat.value == FAVOURITES) -> bandList.value!!.filter { band -> band.isFavourite }
                .toMutableList()
            else -> bandList.value!!.filter { band -> band.category == selectedCat.value }
                .toMutableList()
        }
    }

    fun onFavouriteClick(band: ListElement, isfav: Boolean){
        band.isFavourite = !isfav
    }

    fun onSwipe(index: Int){
        _bandList.value?.removeAt(index)
        _bandList.value = _bandList.value //this notifies observers that the value has changed
    }

    fun loadCategories(cat: Array<String>){ //loads categories from Rescources
        categories = cat
    }



    fun onCategorySelected(categoryPos: Int) {
        _selectedCat.value = categories[categoryPos]
    }

    fun getSelectedCatIndex(): Int {
        val filterArray =  categories
        for ((i, string) in filterArray.withIndex()){
            if (string == selectedCat.value) return i
        }
        return -1
    }
}

/* WARINING  - LOTS OF HARDCODED DATA - realistically we get those from somewhere else*/

val bands = mutableListOf(
    ListElement(1,
        "Beatles",
        "Rock",
        R.drawable.thebeatles,
        "The Beatles were an English rock band formed in Liverpool in 1960. The group, whose best-known line-up comprised John Lennon, Paul McCartney, George Harrison and Ringo Starr, are regarded as the most influential band of all time. They were integral to the development of 1960s counterculture and popular music's recognition as an art form. Rooted in skiffle, beat and 1950s rock and roll, their sound incorporated elements of classical music and traditional pop in innovative ways; the band later explored music styles ranging from ballads and Indian music to psychedelia and hard rock. As pioneers in recording, songwriting and artistic presentation, the Beatles revolutionised many aspects of the music industry and were often publicised as leaders of the era's youth and sociocultural movements.",
        listOf(
            Album("A Hard Day's Night", "1964", R.drawable.beatles_harddaysnight),
            Album("Sgt. Pepper's Lonely Hearts Club Band", "1967", R.drawable.beatles_sgt_peppers_lonely_hearts_club_band),
            Album("White Album", "1968", R.drawable.beatles_whitealbum),
            Album("Abbey Road", "1969", R.drawable.beatles_abbey_road),
            Album("Let It Be", "1970", R.drawable.beatles_letitbe)
            ),
        listOf(R.drawable.beatles_harddaysnight, R.drawable.beatles_sgt_peppers_lonely_hearts_club_band, R.drawable.beatles_whitealbum, R.drawable.beatles_abbey_road, R.drawable.beatles_letitbe),
        false),
    ListElement(2,
        "Fleetwood Mac",
        "Rock",
        R.drawable.fm,
        "Fleetwood Mac are a British-American rock band, formed in London in 1967. Fleetwood Mac were founded by guitarist Peter Green, drummer Mick Fleetwood and guitarist Jeremy Spencer, before bassist John McVie joined the lineup for their self-titled debut album. Danny Kirwan joined as a third guitarist in 1968. Keyboardist Christine Perfect, who contributed as a session musician from the second album, married McVie and joined in 1970.",
        listOf(
            Album("Fleetwood Mac", "1975", R.drawable.fm),
            Album("Rumors", "1976", R.drawable.fmrumors),
            Album("Tusk", "1979", R.drawable.fmtusk),
            Album("Tango in the Night", "1987", R.drawable.fmtintn)
        ),
        listOf(R.drawable.fmtusk, R.drawable.fmtintn, R.drawable.fmrumors, R.drawable.fm),
        false),
    ListElement(3,
        "Jimi Hendrix",
        "Rock",
        R.drawable.jh,
        "James Marshall \"Jimi\" Hendrix (born Johnny Allen Hendrix; November 27, 1942 – September 18, 1970) was an American musician, singer, and songwriter. Although his mainstream career spanned only four years, he is widely regarded as one of the most influential electric guitarists in the history of popular music, and one of the most celebrated musicians of the 20th century. The Rock and Roll Hall of Fame describes him as \"arguably the greatest instrumentalist in the history of rock music\".",
        listOf(
            Album("Are You Experienced", "1967", R.drawable.jh_areyouexp),
            Album("Axis: Bold as Love", "1967", R.drawable.jh_axis),
            Album("Electric Ladyland", "1968", R.drawable.jh_el)
        ),
        listOf(R.drawable.jh, R.drawable.jh_areyouexp, R.drawable.jh_axis, R.drawable.jh_el),
        false),
    ListElement(4,
        "Ed Sheeran",
        "Pop",
        R.drawable.ed,
        "Edward Christopher Sheeran (born 17 February 1991) is an English singer, songwriter, musician, record producer, and actor. After first recording music in 2004, he began gaining attention through YouTube. In early 2011, Sheeran independently released the extended play, No. 5 Collaborations Project. He signed with Asylum Records the same year. Sheeran's debut album, + (pronounced \"plus\"), was released in September 2011 and topped the UK Albums Chart. It contained his first hit single \"The A Team\". In 2012, Sheeran won the Brit Awards for Best British Male Solo Artist and British Breakthrough Act. Sheeran's second studio album, × (pronounced \"multiply\"), was released in June 2014. It was named the second-best-selling album worldwide of 2015. In the same year, × won Album of the Year at the 2015 Brit Awards, and he received the Ivor Novello Award for Songwriter of the Year from the British Academy of Songwriters, Composers and Authors. A single from ×, \"Thinking Out Loud\", earned him the 2016 Grammy Awards for Song of the Year and Best Pop Solo Performance.",
        listOf(
            Album("+", "2011", R.drawable.ed_p),
            Album("x", "2014", R.drawable.ed_x),
            Album("÷", "2017", R.drawable.ed_d)
        ),
        listOf(R.drawable.ed, R.drawable.ed_p, R.drawable.ed_x, R.drawable.ed_d),
        false),
    ListElement(5,
        "Louis Armstrong",
        "Jazz",
        R.drawable.la,
        "Louis Armstrong (1901–1971), nicknamed Satchmo or Pops, was an American trumpeter, composer, singer and occasional actor who was one of the most influential figures in jazz and in all of American popular music. His career spanned five decades, from the 1920s to the 1960s, and different eras in jazz.",
        listOf(
            Album("Ella and Louis", "1956", R.drawable.ellaandlouis),
            Album("I've Got the World on a String", "1960", R.drawable.la_ws)
        ),
        listOf(R.drawable.la, R.drawable.ellaandlouis, R.drawable.la_ws),
        false),
    ListElement(6,
        "John Mayer",
        "Pop",
        R.drawable.jm,
        "John Clayton Mayer (born October 16, 1977) is an American singer-songwriter, guitarist, and record producer. Born in Bridgeport, Connecticut, Mayer attended Berklee College of Music in Boston, but disenrolled and moved to Atlanta in 1997 with Clay Cook. Together, they formed a short-lived two-man band called Lo-Fi Masters. After their split, Mayer continued to play local clubs, refining his skills and gaining a following. After his appearance at the 2001 South by Southwest Festival, he was signed to Aware Records, and then Columbia Records, which released his first EP Inside Wants Out. His following two full-length albums—Room for Squares (2001) and Heavier Things (2003)—did well commercially, achieving multi-platinum status. In 2003, he won the Grammy Award for Best Male Pop Vocal Performance for his single \"Your Body Is a Wonderland\".",
        listOf(
            Album("Continuum", "2006", R.drawable.jm_c),
            Album("Battle Studies", "2009", R.drawable.jm_bs)),
        listOf( R.drawable.jm, R.drawable.jm_c, R.drawable.jm_bs),
        false),
    ListElement(7,
        "Rick Astley",
        "Pop",
        R.drawable.ra,
        "We're no strangers to love\n" +
                "You know the rules and so do I\n" +
                "A full commitment's what I'm thinking of\n" +
                "You wouldn't get this from any other guy\n" +
                "I just wanna tell you how I'm feeling\n" +
                "Gotta make you understand\n" +
                "Never gonna give you up\n" +
                "Never gonna let you down\n" +
                "Never gonna run around and desert you\n" +
                "Never gonna make you cry\n" +
                "Never gonna say goodbye\n" +
                "Never gonna tell a lie and hurt you",
        listOf(
            Album("Never Gonna Give You Up (Single)", "1987", R.drawable.ra_nw),
            Album("Whenever You Need Somebody", "1987", R.drawable.ra_wys)
        ),
        listOf(R.drawable.ra,R.drawable.ra, R.drawable.ra, R.drawable.ra, R.drawable.ra_nw, R.drawable.ra),
        false),
    ListElement(8,
        "Miles Davis",
        "Jazz",
        R.drawable.md,
        "Miles Dewey Davis III (May 26, 1926 – September 28, 1991) was an American jazz trumpeter, bandleader, and composer. He is among the most influential and acclaimed figures in the history of jazz and 20th-century music. Davis adopted a variety of musical directions in a five-decade career that kept him at the forefront of many major stylistic developments in jazz.",
        listOf(
            Album("The New Sounds", "1951", R.drawable.ms_ns),
            Album("Young Man with a Horn", "1952", R.drawable.md_ym)
        ),
        listOf(R.drawable.md, R.drawable.ms_ns, R.drawable.md_ym),
        false)
)




