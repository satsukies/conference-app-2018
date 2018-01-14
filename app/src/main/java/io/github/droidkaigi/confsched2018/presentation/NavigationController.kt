package io.github.droidkaigi.confsched2018.presentation

import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import io.github.droidkaigi.confsched2018.R
import io.github.droidkaigi.confsched2018.model.Session
import io.github.droidkaigi.confsched2018.presentation.about.AboutThisAppActivity
import io.github.droidkaigi.confsched2018.presentation.about.AboutThisAppFragment
import io.github.droidkaigi.confsched2018.presentation.detail.SessionDetailActivity
import io.github.droidkaigi.confsched2018.presentation.detail.SessionDetailFragment
import io.github.droidkaigi.confsched2018.presentation.favorite.FavoriteSessionsFragment
import io.github.droidkaigi.confsched2018.presentation.feed.FeedFragment
import io.github.droidkaigi.confsched2018.presentation.map.MapActivity
import io.github.droidkaigi.confsched2018.presentation.map.MapFragment
import io.github.droidkaigi.confsched2018.presentation.search.SearchFragment
import io.github.droidkaigi.confsched2018.presentation.sessions.SessionsFragment
import io.github.droidkaigi.confsched2018.presentation.settings.SettingsActivity
import io.github.droidkaigi.confsched2018.presentation.settings.SettingsFragment
import io.github.droidkaigi.confsched2018.presentation.speaker.SpeakerDetailActivity
import io.github.droidkaigi.confsched2018.presentation.speaker.SpeakerDetailFragment
import io.github.droidkaigi.confsched2018.presentation.sponsors.SponsorsActivity
import io.github.droidkaigi.confsched2018.presentation.sponsors.SponsorsFragment
import io.github.droidkaigi.confsched2018.presentation.topic.TopicDetailActivity
import io.github.droidkaigi.confsched2018.presentation.topic.TopicDetailFragment
import javax.inject.Inject

class NavigationController @Inject constructor(private val activity: AppCompatActivity) {
    private val containerId: Int = R.id.content
    private val fragmentManager: FragmentManager = activity.supportFragmentManager

    fun navigateToSessions() {
        replaceFragment(SessionsFragment.newInstance())
    }

    fun navigateToSearch() {
        replaceFragment(SearchFragment.newInstance())
    }

    fun navigateToFavoriteSessions() {
        replaceFragment(FavoriteSessionsFragment.newInstance())
    }

    fun navigateToFeed() {
        replaceFragment(FeedFragment.newInstance())
    }

    fun navigateToDetail(sessionId: String) {
        replaceFragment(SessionDetailFragment.newInstance(sessionId))
    }

    fun navigateToMap() {
        replaceFragment(MapFragment.newInstance())
    }

    fun navigateToSponsors() {
        replaceFragment(SponsorsFragment.newInstance())
    }

    fun navigateToSettings() {
        replaceFragment(SettingsFragment.newInstance())
    }

    fun navigateToAboutThisApp() {
        replaceFragment(AboutThisAppFragment.newInstance())
    }

    fun navigateToSpeakerDetail(speakerId: String) {
        replaceFragment(SpeakerDetailFragment.newInstance(speakerId))
    }

    fun navigateToTopicDetail(topicId: Int) {
        replaceFragment(TopicDetailFragment.newInstance(topicId))
    }

    private fun replaceFragment(fragment: Fragment) {
        fragmentManager
                .beginTransaction()
                .replace(containerId, fragment)
                .commitAllowingStateLoss()
    }

    fun navigateToSessionDetailActivity(session: Session) {
        SessionDetailActivity.start(activity, session)
    }

    fun navigateToMapActivity() {
        MapActivity.start(activity)
    }

    fun navigateToSponsorsActivity() {
        SponsorsActivity.start(activity)
    }

    fun navigateToSettingsActivity() {
        SettingsActivity.start(activity)
    }

    fun navigateToAboutThisAppActivity() {
        AboutThisAppActivity.start(activity)
    }

    fun navigateToSpeakerDetailActivity(speakerId: String) {
        SpeakerDetailActivity.start(activity, speakerId)
    }

    fun navigateToTopicDetailActivity(topicId: Int) {
        TopicDetailActivity.start(activity, topicId)
    }

    fun navigateToExternalBrowser(url: String) {
        val intent = CustomTabsIntent.Builder()
                .setShowTitle(true)
                .setToolbarColor(ContextCompat.getColor(activity, R.color.primary))
                .setExitAnimations(
                        activity,
                        android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right
                )
                .build()

        // welcome contributions :)
        // e.g. support in-app browser

        intent.launchUrl(activity, Uri.parse(url))
    }
}
