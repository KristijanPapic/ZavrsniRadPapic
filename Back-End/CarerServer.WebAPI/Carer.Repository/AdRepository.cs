using Carer.Common;
using Carer.Model.DomainModels;
using Carer.Repository.Common;
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Text;
using System.Threading.Tasks;

namespace Carer.Repository
{
    class AdRepository : IAdRepository
    {
        private String connectionString = "Data Source=SQL8004.site4now.net;Initial Catalog=db_a8bd39_carerdb;User Id=db_a8bd39_carerdb_admin;Password=Micamaca12";


        public async Task<List<AdDomainModel>> GetAllAdsAsync(AdFilter filter)
        {
            SqlConnection connection = new SqlConnection(connectionString);
            StringBuilder queryString = new StringBuilder();
            if (filter.Type == 1)
            {
                queryString.Append("SELECT A.Id,Type,Job,Description,PetId,Name,PictureURl,Kind,A.UserId,Username,Email,PhoneNumber,OwnerOverallRating as Rating,Active,A.DateCreated");

                if (filter.ShowStatus)
                {
                    queryString.Append($", AU.stat as Status FROM Ad A INNER JOIN Pet P on P.Id = PetId INNER JOIN [User] U on U.Id = A.UserId INNER JOIN AppliedUser AU on A.Id = AU.AdId WHERE AU.UserId = '{filter.CurrentUser}' AND Type = {filter.Type} AND NOT Active = 4 AND NOT Active = 3");
                }
                else if(filter.ShowAppliedCount)
                {
                    queryString.Append($" FROM Ad A INNER JOIN Pet P on P.Id = PetId INNER JOIN [User] U on U.Id = A.UserId WHERE Type = {filter.Type} AND NOT Active = 4 AND NOT Active = 2");
                }
                else
                {
                    queryString.Append($" FROM Ad A INNER JOIN Pet P on P.Id = PetId INNER JOIN [User] U on U.Id = A.UserId WHERE Type = {filter.Type} AND Active = 0 AND NOT A.UserId = '{filter.CurrentUser}'");
                }

                if (filter.UserId.HasValue)
                {
                    queryString.Append($" AND UserId = '{filter.UserId}'");
                }

                if (!string.IsNullOrEmpty(filter.PetType))
                {
                    queryString.Append($" AND P.Kind = '{filter.PetType}'");
                }

                if (!string.IsNullOrEmpty(filter.JobType))
                {
                    queryString.Append($" AND A.Job = '{filter.JobType}'");
                }

                if (filter.Rating > 0)
                {
                    queryString.Append($" AND U.OwnerOverallRating > {filter.Rating}");
                }


            }
            else
            {
                queryString.Append($"SELECT A.Id,Type,Job,Description,PictureURl,A.UserId,Username,Email,PhoneNumber,CarerOverallRating AS Rating,Active,A.DateCreated");

                if (filter.ShowStatus)
                {
                    queryString.Append($", AU.stat as Status FROM Ad A INNER JOIN [User] U on U.Id = A.UserId INNER JOIN AppliedUser AU on A.Id = AU.AdId WHERE AU.UserId = '{filter.CurrentUser}' AND Type = {filter.Type} AND NOT Active = 4 AND NOT Active = 3");
                }
                else if (filter.ShowAppliedCount)
                {
                    queryString.Append($" FROM Ad A INNER JOIN [User] U on U.Id = A.UserId AND Type = {filter.Type} AND NOT Active = 4 AND NOT Active = 2");
                }
                else
                {
                    queryString.Append($" FROM Ad A INNER JOIN [User] U on U.Id = A.UserId AND Type = {filter.Type} AND Active = 0 AND NOT A.UserId = '{filter.CurrentUser}'");
                }

                if (filter.UserId.HasValue)
                {
                    queryString.Append($" AND UserId = '{filter.UserId}'");
                }

                if (!string.IsNullOrEmpty(filter.JobType))
                {
                    queryString.Append($" AND A.Job = '{filter.JobType}'");
                }

                if (filter.Rating > 0)
                {
                    queryString.Append($" AND U.CarerOverallRating > {filter.Rating}");
                }
            }
            

            SqlCommand command = new SqlCommand(queryString.ToString(), connection);
            SqlDataAdapter adapter = new SqlDataAdapter(command);
            DataSet adData = new DataSet();
            await Task.Run(() => +(adapter.Fill(adData)));
            List<AdDomainModel> ads = new List<AdDomainModel>();
            if (adData.Tables[0].Rows.Count == 0)
            {
                return ads;
            }
            foreach (DataRow dataRow in adData.Tables[0].Rows)
            {
                if (filter.Type == 1)
                {
                    ads.Add(new AdDomainModel
                    {
                        Id = Guid.Parse(Convert.ToString(dataRow["Id"])),
                        Type = Convert.ToByte(dataRow["Type"]),
                        Job = Convert.ToString(dataRow["Job"]),
                        Description = Convert.ToString(dataRow["Description"]),
                        PetId = Guid.Parse(Convert.ToString(dataRow["PetId"])),
                        PetName = Convert.ToString(dataRow["Name"]),
                        PetType = Convert.ToString(dataRow["Kind"]),
                        UserId = Guid.Parse(Convert.ToString(dataRow["UserId"])),
                        UserName = Convert.ToString(dataRow["Username"]),
                        PictureURL = Convert.ToString(dataRow["PictureURl"]),
                        UserEmail = Convert.ToString(dataRow["Email"]),
                        UserPhone = Convert.ToString(dataRow["PhoneNumber"]),
                        UserRating = Convert.ToInt16(dataRow["Rating"]),
                        Active = Convert.ToByte(dataRow["Active"]),
                        Status = filter.ShowStatus ? Convert.ToString(dataRow["Status"]) : "",
                        DateCreated = Convert.ToDateTime(dataRow["DateCreated"]),
                    });
                }
                else
                {
                    ads.Add(new AdDomainModel
                    {
                        Id = Guid.Parse(Convert.ToString(dataRow["Id"])),
                        Type = Convert.ToByte(dataRow["Type"]),
                        Job = Convert.ToString(dataRow["Job"]),
                        Description = Convert.ToString(dataRow["Description"]),
                        UserId = Guid.Parse(Convert.ToString(dataRow["UserId"])),
                        UserName = Convert.ToString(dataRow["Username"]),
                        PictureURL = Convert.ToString(dataRow["PictureURl"]),
                        UserEmail = Convert.ToString(dataRow["Email"]),
                        UserPhone = Convert.ToString(dataRow["PhoneNumber"]),
                        UserRating = Convert.ToInt16(dataRow["Rating"]),
                        Active = Convert.ToByte(dataRow["Active"]),
                        Status = filter.ShowStatus ? Convert.ToString(dataRow["Status"]) : "",
                        DateCreated = Convert.ToDateTime(dataRow["DateCreated"]),
                    });
                }

            }
            return ads;
        }

        public async Task<AdDomainModel> GetAdByIdAsync(Guid id)
        {
            int adType = await GetAdTypeAsync(id);
            SqlConnection connection = new SqlConnection(connectionString);

            StringBuilder queryString = new StringBuilder();
            if (adType == 1)
            {
                queryString.Append("SELECT A.Id,Type,Job,Description,PetId,Name,PictureURl,Kind,A.UserId,Username,Email,PhoneNumber,OwnerOverallRating as Rating,Active,A.DateCreated");

                queryString.Append($" FROM Ad A INNER JOIN Pet P on P.Id = PetId INNER JOIN [User] U on U.Id = A.UserId WHERE A.Id = '{id}'");
            }
            else
            {
                queryString.Append($"SELECT A.Id,Type,Job,Description,PictureURl,A.UserId,Username,Email,PhoneNumber,CarerOverallRating AS Rating,Active,A.DateCreated");

                queryString.Append($" FROM Ad A INNER JOIN [User] U on U.Id = A.UserId WHERE a.Id = '{id}'");
                
            }

            SqlCommand command = new SqlCommand(queryString.ToString(), connection);
            SqlDataAdapter adapter = new SqlDataAdapter(command);
            DataSet adData = new DataSet();
            await Task.Run(() => (adapter.Fill(adData)));
            if (adData.Tables[0].Rows.Count == 0)
            {
                return null;
            }
            DataRow dataRow = adData.Tables[0].Rows[0];
            AdDomainModel ad = new AdDomainModel();
            if (adType == 1)
            {
                ad = new AdDomainModel
                {
                    Id = Guid.Parse(Convert.ToString(dataRow["Id"])),
                    Type = Convert.ToByte(dataRow["Type"]),
                    Job = Convert.ToString(dataRow["Job"]),
                    Description = Convert.ToString(dataRow["Description"]),
                    PetId = Guid.Parse(Convert.ToString(dataRow["PetId"])),
                    PetName = Convert.ToString(dataRow["Name"]),
                    PetType = Convert.ToString(dataRow["Kind"]),
                    UserId = Guid.Parse(Convert.ToString(dataRow["UserId"])),
                    UserName = Convert.ToString(dataRow["Username"]),
                    PictureURL = Convert.ToString(dataRow["PictureURl"]),
                    UserEmail = Convert.ToString(dataRow["Email"]),
                    UserPhone = Convert.ToString(dataRow["PhoneNumber"]),
                    UserRating = Convert.ToInt16(dataRow["Rating"]),
                    Active = Convert.ToInt32(dataRow["Active"]),
                    DateCreated = Convert.ToDateTime(dataRow["DateCreated"]),
                };
            }
            else
            {
                ad = new AdDomainModel
                {
                    Id = Guid.Parse(Convert.ToString(dataRow["Id"])),
                    Type = Convert.ToByte(dataRow["Type"]),
                    Job = Convert.ToString(dataRow["Job"]),
                    Description = Convert.ToString(dataRow["Description"]),
                    UserId = Guid.Parse(Convert.ToString(dataRow["UserId"])),
                    UserName = Convert.ToString(dataRow["Username"]),
                    PictureURL = Convert.ToString(dataRow["PictureURl"]),
                    UserEmail = Convert.ToString(dataRow["Email"]),
                    UserPhone = Convert.ToString(dataRow["PhoneNumber"]),
                    UserRating = Convert.ToInt16(dataRow["Rating"]),
                    Active = Convert.ToInt32(dataRow["Active"]),
                    DateCreated = Convert.ToDateTime(dataRow["DateCreated"]),
                };
            }

            return ad;
        }
        public async Task PostAdAsync(AdDomainModel ad)
        {
            SqlConnection connection = new SqlConnection(connectionString);
            string queryString;
            if (ad.Type == 1)
            {
                queryString = $"INSERT INTO Ad (Id,Type,Job,Description,PetId,UserId,Active) VALUES ('{ad.Id}',{ad.Type},'{ad.Job}','{ad.Description}','{ad.PetId}','{ad.UserId}',{ad.Active})";

            }
            else
            {
                queryString = $"INSERT INTO Ad (Id,Type,Job,Description,UserId,Active) VALUES ('{ad.Id}',{ad.Type},'{ad.Job}','{ad.Description}','{ad.UserId}',{ad.Active})";

            }
            SqlCommand command = new SqlCommand(queryString, connection);
            //command.Parameters.Add("@DATECREATED", SqlDbType.DateTime);
            //command.Parameters["@DATECREATED"].Value = ad.DateCreated;
            connection.Open();
            await command.ExecuteNonQueryAsync();
            connection.Close();
        }

        public async Task PutAdAsync(AdDomainModel ad)
        {
            SqlConnection connection = new SqlConnection(connectionString);
            string queryString;
            if(ad.PetId != null && ad.PetId != Guid.Empty)
            {
                queryString = $"UPDATE Ad SET Description = '{ad.Description}',PetId ='{ad.PetId}',Job = '{ad.Job}',Active = {ad.Active} where Id = '{ad.Id}'";
            }
            else
            {
                queryString = $"UPDATE Ad SET Description = '{ad.Description}',Job = '{ad.Job}',Active = {ad.Active} where Id = '{ad.Id}'";
            }
            SqlCommand command = new SqlCommand(queryString, connection);
            connection.Open();
            await command.ExecuteNonQueryAsync();
            connection.Close();
        }

        public async Task DeleteAdAsync(Guid id)
        {
            SqlConnection connection = new SqlConnection(connectionString);
            string queryString = $"delete from Ad where Id = '{id}'";
            SqlCommand command = new SqlCommand(queryString, connection);
            connection.Open();
            await command.ExecuteNonQueryAsync();
            connection.Close();
        }


        public async Task<int> GetAdTypeAsync(Guid adId)
        {
            SqlConnection connection = new SqlConnection(connectionString);
            string queryString = $"SELECT Type from Ad where Id = '{adId}'";
            SqlCommand command = new SqlCommand(queryString, connection);
            SqlDataAdapter typeAdapter = new SqlDataAdapter(command);
            DataSet typeData = new DataSet();
            await Task.Run(() => (typeAdapter.Fill(typeData)));
            DataRow countDataRow = typeData.Tables[0].Rows[0];
            int type = Convert.ToInt32(countDataRow["Type"]);
            return type;
        }

        public async Task SetActiveAdAsync(Guid adId)
        {
            SqlConnection connection = new SqlConnection(connectionString);
            string queryString = $"UPDATE Ad SET Active = 1 where Id = '{adId}'";
            SqlCommand command = new SqlCommand(queryString, connection);
            connection.Open();
            await command.ExecuteNonQueryAsync();
            connection.Close();
        }

        public void SetStatusAdAsync(Guid adId,int status)
        {
            SqlConnection connection = new SqlConnection(connectionString);
            string queryString = $"UPDATE Ad SET Active = {status} where Id = '{adId}'";
            SqlCommand command = new SqlCommand(queryString, connection);
            connection.Open();
            command.ExecuteNonQuery();
            connection.Close();
        }

        public int GetAdStatusAsync(Guid adId)
        {
            SqlConnection connection = new SqlConnection(connectionString);
            string queryString = $"SELECT Active from Ad where Id = '{adId}'";
            SqlCommand command = new SqlCommand(queryString, connection);
            SqlDataAdapter statusAdapter = new SqlDataAdapter(command);
            DataSet statusData = new DataSet();
            statusAdapter.Fill(statusData);
            DataRow statusDataRow = statusData.Tables[0].Rows[0];
            int status = Convert.ToInt32(statusDataRow["Active"]);
            return status;
        }


    }
}
