# apollo-android-generated-sample

Demo using [apollo-android](https://github.com/apollographql/apollo-android)'s apollo-gradle-plugin to generate java Graphql client code (without android) 


Steps used to generated this sample 
1. Run gradlew build
 - graphql files read from [apollo-sample/src/main/graphql/com/apollographql/apollo/sample]
 - generated code was under `apollo-sample/build/generated/source/apollo/classes`
 - generated code moved to [apollo-sample/src/main] and saved/pushed into this repo
 
2. Demo/test call added: 
 - in [apollo-sample/src/test/java/com/apollographql/apollo/sample/FeedQueryTest]
 - the demo request currently fail due to certificate problem

