-include .env

# Please add a .env file with the AUTH_TOKEN_BASIC for bonsaisearch

BASE_URL=https://kafka-twitter-5225177207.eu-central-1.bonsaisearch.net
ELASTIC_INDEX=twitter
ELASTICSEARCH_ID=1358372433292496896

health:
	curl --location --request GET '${BASE_URL}/_cat/health?v' \
	--header 'Authorization: Basic ${AUTH_TOKEN_BASIC}'

nodes:
	curl --location --request GET '${BASE_URL}/_cat/nodes?v' \
	--header 'Authorization: Basic ${AUTH_TOKEN_BASIC}'

list-indices:
	curl --location --request GET '${BASE_URL}/_cat/indices?v' \
	--header 'Authorization: Basic ${AUTH_TOKEN_BASIC}'

create-index:
	curl --location --request PUT '${BASE_URL}/${ELASTIC_INDEX}' \
	--header 'Authorization: Basic ${AUTH_TOKEN_BASIC}' | jq

delete-index:
	curl --location --request DELETE '${BASE_URL}/${ELASTIC_INDEX}' \
	--header 'Authorization: Basic ${AUTH_TOKEN_BASIC}' | jq

put-doc:
	curl --location --request PUT '${BASE_URL}/${ELASTIC_INDEX}/_doc/1' \
	--header 'Authorization: Basic ${AUTH_TOKEN_BASIC}' \
	--header 'Content-Type: application/json' \
	--data-raw '{ \
		"course": "kafka", \
		"from": "udemy", \
		"module": "ElasticSearch" \
	}' | jq

delete-doc:
	curl --location --request DELETE '${BASE_URL}/${ELASTIC_INDEX}/_doc/${ELASTIC_SEARCH_ID}' \
	--header 'Authorization: Basic ${AUTH_TOKEN_BASIC}' | jq

get-doc:
	curl --location --request GET '${BASE_URL}/${ELASTIC_INDEX}/_doc/${ELASTICSEARCH_ID}' \
	--header 'Authorization: Basic ${AUTH_TOKEN_BASIC}' | jq

