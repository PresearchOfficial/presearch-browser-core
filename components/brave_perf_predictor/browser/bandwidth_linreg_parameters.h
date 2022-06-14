/* Copyright 2019 The Brave Authors. All rights reserved.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */

#ifndef BRAVE_COMPONENTS_BRAVE_PERF_PREDICTOR_BROWSER_BANDWIDTH_LINREG_PARAMETERS_H_
#define BRAVE_COMPONENTS_BRAVE_PERF_PREDICTOR_BROWSER_BANDWIDTH_LINREG_PARAMETERS_H_

/* This file is automatically generated, do not edit directly */

#include <string>
#include <array>

#include "base/containers/flat_set.h"
#include "base/containers/flat_map.h"

namespace brave_perf_predictor {

constexpr double model_intercept = 5.085407773814489;
constexpr int feature_count = 213;
constexpr std::array<double, feature_count> model_coefficients = {
0.12337659866050482,
-0.0,
0.0,
0.0,
0.0,
0.006894977221883717,
-0.0053057834130837605,
0.012862385567785137,
-0.022571575512130217,
0.0,
0.03128111390815784,
-0.008905984853438976,
0.0,
-0.012493676378740588,
-0.01817560552389195,
-0.008378072321895977,
0.0,
0.0,
0.002283231356917989,
0.048919735990114545,
0.0,
0.0,
-0.0,
0.03710665420979194,
0.014291396463246602,
-0.0,
0.102892550977104,
0.052414725498173366,
0.0,
0.25621205523724505,
0.39537140102916657,
0.42410309202890667,
0.25546300017617013,
-0.0,
0.11108001260714759,
0.0,
0.0,
-0.0,
0.369347502104488,
-0.0621473488525576,
0.0,
-0.19225865475037224,
0.009684176926811635,
-0.0,
0.5984031539555243,
0.0,
-0.0,
0.0,
0.0,
-0.0,
-0.15442052414735336,
0.5819796534516541,
0.07669089364860023,
-0.0,
0.07996581319924465,
0.0,
0.08300572314082845,
0.27985657360814103,
-0.31342877799575186,
0.0,
0.0,
0.0,
-0.0,
0.21725044259235132,
-0.0,
0.0,
-0.6601745015855838,
0.47381119601381766,
0.0,
0.03181202307277254,
0.0,
-0.0,
0.0,
0.0,
0.0,
0.41982354308978265,
-0.0,
-0.0,
0.0,
-0.15701236163909485,
0.05143595038841564,
-0.0,
-1.3967255076899114,
0.012164951411760265,
-0.16190436401823916,
0.0,
0.3220696681595943,
-0.1082526947395474,
0.0,
0.2810643037362584,
0.3475914458610097,
0.0,
-0.0,
0.2159865469721421,
-0.0,
0.0,
-0.34943241394679975,
-0.15571843423959625,
0.2937142943175603,
-0.0,
-0.0,
-0.6666677255779397,
0.03158187579337792,
-0.0,
-0.0,
-0.0,
-0.4825339029883909,
-0.10536520325606542,
0.0,
0.0,
0.18019166627132638,
-0.0,
0.060628635086959023,
0.1535326725086174,
0.0,
0.0,
0.41012738584685204,
-0.0,
-3.9714104351330406e-06,
-0.054919851303674425,
-0.02084871656373664,
-0.0,
0.08831247903148058,
-0.0,
0.3576186594301512,
-0.0,
0.2045708428966265,
-0.0,
-0.22633684673764687,
0.33920619628350673,
0.4087551349593832,
0.00014400861713193862,
-0.0,
0.009788614358444537,
0.6882150688542139,
-0.09819510616132636,
0.0,
0.0,
0.0,
0.24647318516426506,
0.0,
0.0,
0.2974896639283871,
-0.0,
0.1767238117388548,
0.0971655540387981,
0.09612967228805114,
0.0,
-0.47559667034732106,
-0.48262999096637776,
-0.0,
-0.14856884527780762,
-0.0,
0.0,
0.0,
-0.06867861932624512,
0.0,
0.0,
0.0,
-0.0,
-0.0,
-0.0,
-0.0,
-0.0,
-0.07016021046322078,
-0.19673925717707294,
0.4279422735214434,
0.07935762516999385,
0.0,
0.0,
0.001748304149647706,
-0.0,
-0.0,
-0.0,
-0.01305037928240179,
-0.0,
0.2558178171151348,
0.0013887823536121427,
-0.4098203655723224,
-0.0,
-0.0,
-0.1250453331155213,
0.0,
0.0,
0.10399781691346788,
0.005453211270251716,
-0.0,
0.03981426371078261,
1.157031326454923,
0.0,
-0.0,
0.0,
0.0,
0.4259520667147177,
-0.0,
6.557171791471991e-07,
0.014181325019371424,
0.0,
-0.09220432982116501,
0.0,
0.0,
0.0,
-0.0,
-0.0,
-0.1103248978017494,
-0.0,
-0.03628699126870285,
-0.08325838279176839,
0.0,
-0.0,
-0.3706138376948898,
-2.0791965507275406e-05,
-0.0
};

constexpr unsigned int standardise_feat_count = 23;

constexpr std::array<double, standardise_feat_count> standardise_feat_means = {
11.155786350148368,
1016.2856083086053,
1181.3004451038576,
717.0712166172107,
1987.0066765578636,
2.4109792284866467,
61992.13946587537,
4.561572700296736,
149840.9243323442,
35.32047477744807,
955033.309347181,
0.13427299703264095,
15261.759643916914,
8.208456973293769,
246940.89243323443,
23.16765578635015,
495043.6083086053,
6.2974777448071215,
103430.64317507419,
48.926557863501486,
974124.4658753709,
80.10089020771514,
2027543.2767062315
};

constexpr std::array<double, standardise_feat_count> standardise_feat_scale = {
12.073811227476089,
926.9436100765363,
979.2250013928962,
779.8742124298378,
1919.1239982290276,
2.102760215714359,
87820.15974403986,
3.575166724949133,
280657.60316361417,
36.691628938982134,
2053276.1547853663,
3.22967240113416,
205275.94312591644,
14.301294363369117,
2396278.961167811,
24.39484351361213,
432881.54561501107,
10.733199259238853,
129287.31378334873,
52.84516132149959,
2258334.3952942668,
57.77650764802925,
3333644.900695055
};

const std::array<std::string, feature_count> feature_sequence{
    "adblockRequests",
    "metrics.firstMeaningfulPaint",
    "metrics.observedDomContentLoaded",
    "metrics.observedFirstVisualChange",
    "metrics.observedLoad",
    "resources.document.requestCount",
    "resources.document.size",
    "resources.font.requestCount",
    "resources.font.size",
    "resources.image.requestCount",
    "resources.image.size",
    "resources.media.requestCount",
    "resources.media.size",
    "resources.other.requestCount",
    "resources.other.size",
    "resources.script.requestCount",
    "resources.script.size",
    "resources.stylesheet.requestCount",
    "resources.stylesheet.size",
    "resources.third-party.requestCount",
    "resources.third-party.size",
    "resources.total.requestCount",
    "resources.total.size",
    "thirdParties.Google Analytics.blocked",
    "thirdParties.Facebook.blocked",
    "thirdParties.Google CDN.blocked",
    "thirdParties.Twitter.blocked",
    "thirdParties.Other Google APIs/SDKs.blocked",
    "thirdParties.Scorecard Research.blocked",
    "thirdParties.Sortable.blocked",
    "thirdParties.Google/Doubleclick Ads.blocked",
    "thirdParties.Adobe Tag Manager.blocked",
    "thirdParties.Google Tag Manager.blocked",
    "thirdParties.Chartbeat.blocked",
    "thirdParties.Amazon Ads.blocked",
    "thirdParties.Salesforce.blocked",
    "thirdParties.Adobe Test & Target.blocked",
    "thirdParties.YouTube.blocked",
    "thirdParties.Outbrain.blocked",
    "thirdParties.Tumblr.blocked",
    "thirdParties.WordPress.blocked",
    "thirdParties.Bing Ads.blocked",
    "thirdParties.New Relic.blocked",
    "thirdParties.JuicyAds.blocked",
    "thirdParties.Audience 360.blocked",
    "thirdParties.Revcontent.blocked",
    "thirdParties.Pubmatic.blocked",
    "thirdParties.AppNexus.blocked",
    "thirdParties.SpotXchange.blocked",
    "thirdParties.AOL / Oath / Verizon Media.blocked",
    "thirdParties.Amazon Web Services.blocked",
    "thirdParties.LoopMe.blocked",
    "thirdParties.Quantcast.blocked",
    "thirdParties.Click4Assistance.blocked",
    "thirdParties.Hotjar.blocked",
    "thirdParties.Snapchat.blocked",
    "thirdParties.jQuery CDN.blocked",
    "thirdParties.Segment.blocked",
    "thirdParties.Usabilla.blocked",
    "thirdParties.Nativo.blocked",
    "thirdParties.Sharethrough.blocked",
    "thirdParties.Twitter Online Conversion Tracking.blocked",
    "thirdParties.BounceX.blocked",
    "thirdParties.Integral Ad Science.blocked",
    "thirdParties.Rubicon Project.blocked",
    "thirdParties.Index Exchange.blocked",
    "thirdParties.Sentry.blocked",
    "thirdParties.Cloudflare CDN.blocked",
    "thirdParties.VigLink.blocked",
    "thirdParties.Optimizely.blocked",
    "thirdParties.Ensighten.blocked",
    "thirdParties.Criteo.blocked",
    "thirdParties.Nielsen NetRatings SiteCensus.blocked",
    "thirdParties.Cookie-Script.com.blocked",
    "thirdParties.Rackspace.blocked",
    "thirdParties.Adobe TypeKit.blocked",
    "thirdParties.Stripe.blocked",
    "thirdParties.Trust Pilot.blocked",
    "thirdParties.Polyfill service.blocked",
    "thirdParties.Affiliate Window.blocked",
    "thirdParties.FontAwesome CDN.blocked",
    "thirdParties.Bootstrap CDN.blocked",
    "thirdParties.Auto Link Maker.blocked",
    "thirdParties.Embedly.blocked",
    "thirdParties.JSDelivr CDN.blocked",
    "thirdParties.OneSignal.blocked",
    "thirdParties.The Trade Desk.blocked",
    "thirdParties.Instagram.blocked",
    "thirdParties.PayPal.blocked",
    "thirdParties.Taboola.blocked",
    "thirdParties.Opentag.blocked",
    "thirdParties.Brightcove.blocked",
    "thirdParties.VWO.blocked",
    "thirdParties.Rambler.blocked",
    "thirdParties.Media Math.blocked",
    "thirdParties.Google Maps.blocked",
    "thirdParties.Unpkg.blocked",
    "thirdParties.Yandex Share.blocked",
    "thirdParties.Yandex Metrica.blocked",
    "thirdParties.Yandex CDN.blocked",
    "thirdParties.Amplitude Mobile Analytics.blocked",
    "thirdParties.Yahoo!.blocked",
    "thirdParties.Yandex Ads.blocked",
    "thirdParties.piano.blocked",
    "thirdParties.Moat.blocked",
    "thirdParties.Parse.ly.blocked",
    "thirdParties.Unruly Media.blocked",
    "thirdParties.Skimbit.blocked",
    "thirdParties.ZenDesk.blocked",
    "thirdParties.Silverpop.blocked",
    "thirdParties.AddThis.blocked",
    "thirdParties.Polldaddy.blocked",
    "thirdParties.Dailymotion.blocked",
    "thirdParties.Disqus.blocked",
    "thirdParties.Alexa.blocked",
    "thirdParties.Mailchimp.blocked",
    "thirdParties.Tealium.blocked",
    "thirdParties.LiveChat.blocked",
    "thirdParties.DemandBase.blocked",
    "thirdParties.Tencent.blocked",
    "thirdParties.Oracle Recommendations On Demand.blocked",
    "thirdParties.Mixpanel.blocked",
    "thirdParties.PerimeterX Bot Defender.blocked",
    "thirdParties.Evidon.blocked",
    "thirdParties.Media.net.blocked",
    "thirdParties.Ghostery Enterprise.blocked",
    "thirdParties.LongTail Ad Solutions.blocked",
    "thirdParties.Sailthru.blocked",
    "thirdParties.Marketplace Web Service.blocked",
    "thirdParties.Pinterest.blocked",
    "thirdParties.BrightTag / Signal.blocked",
    "thirdParties.mPulse.blocked",
    "thirdParties.ForeSee.blocked",
    "thirdParties.Permutive.blocked",
    "thirdParties.FirstImpression.blocked",
    "thirdParties.Connatix.blocked",
    "thirdParties.Media Management Technologies.blocked",
    "thirdParties.Mobify.blocked",
    "thirdParties.Yieldify.blocked",
    "thirdParties.Crazy Egg.blocked",
    "thirdParties.SurveyMonkey.blocked",
    "thirdParties.Touch Commerce.blocked",
    "thirdParties.RichRelevance.blocked",
    "thirdParties.Reevoo.blocked",
    "thirdParties.Micropat.blocked",
    "thirdParties.Playbuzz.blocked",
    "thirdParties.Po.st.blocked",
    "thirdParties.Fastly.blocked",
    "thirdParties.eBay.blocked",
    "thirdParties.TRUSTe.blocked",
    "thirdParties.Qualtrics.blocked",
    "thirdParties.Aggregate Knowledge.blocked",
    "thirdParties.Digioh.blocked",
    "thirdParties.Gigya.blocked",
    "thirdParties.Crowd Control.blocked",
    "thirdParties.LinkedIn Ads.blocked",
    "thirdParties.Riskified.blocked",
    "thirdParties.BlueKai.blocked",
    "thirdParties.AMP.blocked",
    "thirdParties.eXelate.blocked",
    "thirdParties.Captify Media.blocked",
    "thirdParties.Hola Networks.blocked",
    "thirdParties.Polar Mobile Group.blocked",
    "thirdParties.Apester.blocked",
    "thirdParties.StreamRail.blocked",
    "thirdParties.SpringServer.blocked",
    "thirdParties.Monetate.blocked",
    "thirdParties.Adobe Scene7.blocked",
    "thirdParties.Opta.blocked",
    "thirdParties.FLXone.blocked",
    "thirdParties.Sift Science.blocked",
    "thirdParties.Accuweather.blocked",
    "thirdParties.Lucky Orange.blocked",
    "thirdParties.AWeber.blocked",
    "thirdParties.Salesforce.com.blocked",
    "thirdParties.Wistia.blocked",
    "thirdParties.Histats.blocked",
    "thirdParties.ShareThis.blocked",
    "thirdParties.Adyoulike.blocked",
    "thirdParties.Pusher.blocked",
    "thirdParties.PERFORM.blocked",
    "thirdParties.Pingdom RUM.blocked",
    "thirdParties.Cloudflare.blocked",
    "thirdParties.Hubspot.blocked",
    "thirdParties.Curalate.blocked",
    "thirdParties.Decibel Insight.blocked",
    "thirdParties.Mouseflow.blocked",
    "thirdParties.Symantec.blocked",
    "thirdParties.Proper Media.blocked",
    "thirdParties.Vimeo.blocked",
    "thirdParties.LivePerson.blocked",
    "thirdParties.Clicktale.blocked",
    "thirdParties.iPerceptions.blocked",
    "thirdParties.Sekindo.blocked",
    "thirdParties.OpenX.blocked",
    "thirdParties.Teads.blocked",
    "thirdParties.sovrn.blocked",
    "thirdParties.GumGum.blocked",
    "thirdParties.Microsoft Hosted Libs.blocked",
    "thirdParties.Vox Media.blocked",
    "thirdParties.Concert.blocked",
    "thirdParties.Xaxis.blocked",
    "thirdParties.unpkg.blocked",
    "thirdParties.Maxymiser.blocked",
    "thirdParties.Kaltura Video Platform.blocked",
    "thirdParties.SoundCloud.blocked",
    "thirdParties.Kargo.blocked",
    "thirdParties.TripleLift.blocked",
    "thirdParties.Trip Advisor.blocked",
    "thirdParties.Cloudinary.blocked",
    "thirdParties.Tawk.to.blocked",
    "thirdParties.Klevu Search.blocked",
    "thirdParties.Yandex APIs.blocked",
};

const std::array<std::string, 190> relevant_entities{
  "Google Analytics",
  "Facebook",
  "Google CDN",
  "Twitter",
  "Other Google APIs/SDKs",
  "Scorecard Research",
  "Sortable",
  "Google/Doubleclick Ads",
  "Adobe Tag Manager",
  "Google Tag Manager",
  "Chartbeat",
  "Amazon Ads",
  "Salesforce",
  "Adobe Test & Target",
  "YouTube",
  "Outbrain",
  "Tumblr",
  "WordPress",
  "Bing Ads",
  "New Relic",
  "JuicyAds",
  "Audience 360",
  "Revcontent",
  "Pubmatic",
  "AppNexus",
  "SpotXchange",
  "AOL / Oath / Verizon Media",
  "Amazon Web Services",
  "LoopMe",
  "Quantcast",
  "Click4Assistance",
  "Hotjar",
  "Snapchat",
  "jQuery CDN",
  "Segment",
  "Usabilla",
  "Nativo",
  "Sharethrough",
  "Twitter Online Conversion Tracking",
  "BounceX",
  "Integral Ad Science",
  "Rubicon Project",
  "Index Exchange",
  "Sentry",
  "Cloudflare CDN",
  "VigLink",
  "Optimizely",
  "Ensighten",
  "Criteo",
  "Nielsen NetRatings SiteCensus",
  "Cookie-Script.com",
  "Rackspace",
  "Adobe TypeKit",
  "Stripe",
  "Trust Pilot",
  "Polyfill service",
  "Affiliate Window",
  "FontAwesome CDN",
  "Bootstrap CDN",
  "Auto Link Maker",
  "Embedly",
  "JSDelivr CDN",
  "OneSignal",
  "The Trade Desk",
  "Instagram",
  "PayPal",
  "Taboola",
  "Opentag",
  "Brightcove",
  "VWO",
  "Rambler",
  "Media Math",
  "Google Maps",
  "Unpkg",
  "Yandex Share",
  "Yandex Metrica",
  "Yandex CDN",
  "Amplitude Mobile Analytics",
  "Yahoo!",
  "Yandex Ads",
  "piano",
  "Moat",
  "Parse.ly",
  "Unruly Media",
  "Skimbit",
  "ZenDesk",
  "Silverpop",
  "AddThis",
  "Polldaddy",
  "Dailymotion",
  "Disqus",
  "Alexa",
  "Mailchimp",
  "Tealium",
  "LiveChat",
  "DemandBase",
  "Tencent",
  "Oracle Recommendations On Demand",
  "Mixpanel",
  "PerimeterX Bot Defender",
  "Evidon",
  "Media.net",
  "Ghostery Enterprise",
  "LongTail Ad Solutions",
  "Sailthru",
  "Marketplace Web Service",
  "Pinterest",
  "BrightTag / Signal",
  "mPulse",
  "ForeSee",
  "Permutive",
  "FirstImpression",
  "Connatix",
  "Media Management Technologies",
  "Mobify",
  "Yieldify",
  "Crazy Egg",
  "SurveyMonkey",
  "Touch Commerce",
  "RichRelevance",
  "Reevoo",
  "Micropat",
  "Playbuzz",
  "Po.st",
  "Fastly",
  "eBay",
  "TRUSTe",
  "Qualtrics",
  "Aggregate Knowledge",
  "Digioh",
  "Gigya",
  "Crowd Control",
  "LinkedIn Ads",
  "Riskified",
  "BlueKai",
  "AMP",
  "eXelate",
  "Captify Media",
  "Hola Networks",
  "Polar Mobile Group",
  "Apester",
  "StreamRail",
  "SpringServer",
  "Monetate",
  "Adobe Scene7",
  "Opta",
  "FLXone",
  "Sift Science",
  "Accuweather",
  "Lucky Orange",
  "AWeber",
  "Salesforce.com",
  "Wistia",
  "Histats",
  "ShareThis",
  "Adyoulike",
  "Pusher",
  "PERFORM",
  "Pingdom RUM",
  "Cloudflare",
  "Hubspot",
  "Curalate",
  "Decibel Insight",
  "Mouseflow",
  "Symantec",
  "Proper Media",
  "Vimeo",
  "LivePerson",
  "Clicktale",
  "iPerceptions",
  "Sekindo",
  "OpenX",
  "Teads",
  "sovrn",
  "GumGum",
  "Microsoft Hosted Libs",
  "Vox Media",
  "Concert",
  "Xaxis",
  "unpkg",
  "Maxymiser",
  "Kaltura Video Platform",
  "SoundCloud",
  "Kargo",
  "TripleLift",
  "Trip Advisor",
  "Cloudinary",
  "Tawk.to",
  "Klevu Search",
  "Yandex APIs",
};

const base::flat_set<std::string> relevant_entity_set(
    relevant_entities.begin(),
    relevant_entities.end());

struct stdfactor {
  double mean, scale;
};

const base::flat_map<std::string, stdfactor> stdfactor_map = {
  {
    "adblockRequests",
    { 11.155786350148368, 12.073811227476089 }
  },
  {
    "metrics.firstMeaningfulPaint",
    { 1016.2856083086053, 926.9436100765363 }
  },
  {
    "metrics.observedDomContentLoaded",
    { 1181.3004451038576, 979.2250013928962 }
  },
  {
    "metrics.observedFirstVisualChange",
    { 717.0712166172107, 779.8742124298378 }
  },
  {
    "metrics.observedLoad",
    { 1987.0066765578636, 1919.1239982290276 }
  },
  {
    "resources.document.requestCount",
    { 2.4109792284866467, 2.102760215714359 }
  },
  {
    "resources.document.size",
    { 61992.13946587537, 87820.15974403986 }
  },
  {
    "resources.font.requestCount",
    { 4.561572700296736, 3.575166724949133 }
  },
  {
    "resources.font.size",
    { 149840.9243323442, 280657.60316361417 }
  },
  {
    "resources.image.requestCount",
    { 35.32047477744807, 36.691628938982134 }
  },
  {
    "resources.image.size",
    { 955033.309347181, 2053276.1547853663 }
  },
  {
    "resources.media.requestCount",
    { 0.13427299703264095, 3.22967240113416 }
  },
  {
    "resources.media.size",
    { 15261.759643916914, 205275.94312591644 }
  },
  {
    "resources.other.requestCount",
    { 8.208456973293769, 14.301294363369117 }
  },
  {
    "resources.other.size",
    { 246940.89243323443, 2396278.961167811 }
  },
  {
    "resources.script.requestCount",
    { 23.16765578635015, 24.39484351361213 }
  },
  {
    "resources.script.size",
    { 495043.6083086053, 432881.54561501107 }
  },
  {
    "resources.stylesheet.requestCount",
    { 6.2974777448071215, 10.733199259238853 }
  },
  {
    "resources.stylesheet.size",
    { 103430.64317507419, 129287.31378334873 }
  },
  {
    "resources.third-party.requestCount",
    { 48.926557863501486, 52.84516132149959 }
  },
  {
    "resources.third-party.size",
    { 974124.4658753709, 2258334.3952942668 }
  },
  {
    "resources.total.requestCount",
    { 80.10089020771514, 57.77650764802925 }
  },
  {
    "resources.total.size",
    { 2027543.2767062315, 3333644.900695055 }
  },
};

}  // namespace brave_perf_predictor

#endif  // BRAVE_COMPONENTS_BRAVE_PERF_PREDICTOR_BROWSER_BANDWIDTH_LINREG_PARAMETERS_H_
