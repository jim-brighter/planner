FROM nginx:1.21-alpine
RUN rm -rf /usr/share/nginx/html/*
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY dist/* /usr/share/nginx/html
CMD ["nginx", "-g", "daemon off;"]
