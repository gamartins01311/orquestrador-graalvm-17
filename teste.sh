#!/bin/bash

cpfs=(
  "12345678900"
  "98765432100"
  "11122233344"
  "55566677788"
  "99988877766"
  "44455566677"
  "22233344455"
  "66677788899"
  "77788899900"
  "88899900011"
)

total_time_novt=0
count_novt=0

function make_request {
  local url=$1

  local start_time=$(date +%s%3N)
  curl --silent --location --request GET "$url" \
    --header 'User-Agent: Apidog/1.0.0 (https://apidog.com)' \
    --header 'Accept: */*' \
    --header 'Host: 192.168.15.28' \
    --header 'Connection: keep-alive' \
    > /dev/null 2>&1
  local end_time=$(date +%s%3N)

  local response_time=$((end_time - start_time))
  echo "$response_time"
}

for round in {1..10}; do
  for cpf in "${cpfs[@]}"; do
    time_novt=$(make_request "http://192.168.15.28/orquestrador/api/java21/contratos/novt/$cpf")
    total_time_novt=$((total_time_novt + time_novt))
    count_novt=$((count_novt + 1))
    if (( count_novt % 10 == 0 )); then
      avg_novt=$((total_time_novt / count_novt))
      echo "[Preview-SemVirtualThread] /contratos/novt => $count_novt chamadas, média atual: ${avg_novt}ms"
    fi

  done
done

avg_time_novt=$((total_time_novt / count_novt))

echo -e "\nResumo Final - Tempo de Resposta (em milissegundos)"
echo -e "------------------------------------------------------"
echo -e "| Endpoint              | Total Chamadas | Tempo Médio |"
echo -e "------------------------------------------------------"
echo -e "| /contratos/novt       | $count_novt         | ${avg_time_novt} ms    |"
echo -e "------------------------------------------------------"
