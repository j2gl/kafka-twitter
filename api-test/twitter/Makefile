-include .env

# Please add a .env file with the AUTH_TOKEN for twitter

get-tweet:
	curl -X GET "https://api.twitter.com/2/tweets/20?expansions=author_id" \
	--header "Authorization: Bearer ${AUTH_TOKEN}" \
	| jq

get-tweet-picture:
	curl -X GET "https://api.twitter.com/2/tweets/440322224407314432?expansions=author_id,attachments.media_keys" \
	--header "Authorization: Bearer ${AUTH_TOKEN}" \
	| jq

get-tweet-polls:
	curl -X GET "https://api.twitter.com/2/tweets/1028039268030210048?expansions=author_id,attachments.poll_ids" \
	--header "Authorization: Bearer ${AUTH_TOKEN}" \
	| jq

get-tweet-2:
	curl -X GET "https://api.twitter.com/2/tweets?ids=1345526707923668992&tweet.fields=created_at&expansions=author_id&user.fields=created_at" \
	--header "Authorization: Bearer ${AUTH_TOKEN}" \
	| jq

get-tweet-3:
	curl -X GET "https://api.twitter.com/2/tweets/1345526707923668992?tweet.fields=created_at&expansions=author_id&user.fields=created_at" \
	--header "Authorization: Bearer ${AUTH_TOKEN}" \
	| jq
